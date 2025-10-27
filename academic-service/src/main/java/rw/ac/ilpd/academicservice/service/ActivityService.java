package rw.ac.ilpd.academicservice.service;

import feign.FeignException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.ws.rs.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import rw.ac.ilpd.academicservice.exception.ConflictException;
import rw.ac.ilpd.academicservice.integration.client.RoomClient;
import rw.ac.ilpd.academicservice.mapper.ActivityLevelMapper;
import rw.ac.ilpd.academicservice.mapper.ActivityMapper;
import rw.ac.ilpd.academicservice.model.nosql.document.Activity;
import rw.ac.ilpd.academicservice.model.nosql.document.ActivityType;
import rw.ac.ilpd.academicservice.model.nosql.embedding.ActivityLevel;
import rw.ac.ilpd.academicservice.model.nosql.embedding.ActivityOccurrence;
import rw.ac.ilpd.academicservice.model.nosql.embedding.AttendanceMissing;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.StudyModeSession;
import rw.ac.ilpd.academicservice.repository.nosql.ActivityRepository;
import rw.ac.ilpd.sharedlibrary.dto.activity.ActivityRequest;
import rw.ac.ilpd.sharedlibrary.dto.activity.ActivityResponse;
import rw.ac.ilpd.sharedlibrary.dto.activitylevel.ActivityLevelRequest;
import rw.ac.ilpd.sharedlibrary.dto.activitylevel.ActivityLevelResponse;
import rw.ac.ilpd.sharedlibrary.dto.pagination.PagedResponse;
import rw.ac.ilpd.sharedlibrary.enums.ActivityLevelLevels;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ActivityService {

    private final ActivityRepository activityRepository;

    private final ActivityMapper activityMapper;
    private final ActivityLevelMapper activityLevelMapper;
    private final IntakeService intakeService;
    private final ComponentService componentService;
    private final ModuleService moduleService;
    private final StudyModeSessionService studyModeSessionService;
    private final ActivityTypeService activityTypeService;
    private final LecturerService lecturerService;
    private final RoomClient roomClient;

    // ------------------ ACTIVITY ------------------

    public ActivityResponse createActivity(ActivityRequest request) {
        System.out.println("************ activitylevels = " + request.getActivityLevelRequests().getFirst());
        Activity activity = activityMapper.toActivity(request);
        System.out.println("************ activitylevels mapped = " + request.getActivityLevelRequests().getFirst());
        // check for activityType
        ActivityType activityType = activityTypeService.getEntity(request.getActivityType())
                .orElseThrow(() -> new EntityNotFoundException("Activty Type does not exist"));

        // check intakeId if exists
        if(request.getIntakeId() != null){
            if(intakeService.getEntity(UUID.fromString(request.getIntakeId())).isEmpty()){
                throw new EntityNotFoundException("No Intake found with this id");
            }
        }

        // limit the array lenght to 1
        if(request.getActivityLevelRequests().size() > 1){
            throw new IllegalArgumentException("Please provide 1 level per activity");
        }

        // check roomId if exists
        try {
            UUID roomId = UUID.fromString(request.getRoomId());
            roomClient.get(roomId);
        } catch (IllegalArgumentException e) {
            throw new BadRequestException("Invalid Room ID format: " + request.getRoomId());
        } catch (FeignException.NotFound e) {
            throw new EntityNotFoundException("Room not found with ID: " + request.getRoomId());
        } catch (FeignException e) {
            System.out.println("**********************"+e.getMessage());
            throw new RuntimeException("Failed to fetch room from remote service: " + e.getMessage(), e);
        }
        System.out.println("********************** passed");

        // TODO set createdBy

        // check componentId if exists
        if(activityType.getName().equals("LECTURE")){
            if(request.getComponentId() != null) {
                if (componentService.getEntity(UUID.fromString(request.getComponentId())).isEmpty())
                    throw new EntityNotFoundException("No Component found with this id");
            }else
                throw new BadRequestException("Component should be given if the activity is a Lecture");

            if(request.getIntakeId() != null) {
                if (intakeService.getEntity(UUID.fromString(request.getIntakeId())).isEmpty())
                    throw new EntityNotFoundException("No Intake found with this id");
            }else
                throw new BadRequestException("Intake should be given if the activity is a Lecture");
        }

        // check moduleId if exists
        if(request.getModuleId() != null){
            if(moduleService.getEntity(UUID.fromString(request.getModuleId())).isEmpty())
                throw new EntityNotFoundException("No Module found with this id");
        }
        // check lecturerId if exists
        if(request.getLecturerId() != null){
            if(lecturerService.getEntity(request.getLecturerId()).isEmpty()){
                throw new EntityNotFoundException("No Lecturer found with this id");
            }
        }

        // Create all the activity occurences
        List<ActivityOccurrence> newOccurrences = generateOccurencesFromActivity(activity);

        // check if no activity (lecture) already exists for this intake at the same time or in collapsing time in case of a lecture
        if(request.getIntakeId() != null && activityType.getName().equals("LECTURE")) {
            // get all the occurences of this intake
            List<ActivityOccurrence> activityOccurrencesIntake = activityRepository.findActivitiesByActivityLevelsLevelAndActivityLevelsLevelRefId(ActivityLevelLevels.INTAKE, UUID.fromString(request.getIntakeId()))
                    .stream()
                    .filter(x -> x.getActivityTypeId().equals(activityType.getId()))
                    .filter(x -> !x.getEndDay().isBefore(activity.getStartDay()))
                    .flatMap(activity1 -> activity1.getActivityOccurrences().stream())
                    .toList();

            // Hash map of activity occurences by day
            Map<LocalDate, List<ActivityOccurrence>> existingByDay = activityOccurrencesIntake.stream()
                    .collect(Collectors.groupingBy(ActivityOccurrence::getDay));

            // set of all dates and ranges
//            Set<ActivityOccurenceTimeRange> timeRanges = activityOccurrencesIntake.stream()
//                    .map(x -> new ActivityOccurenceTimeRange(x.getDay(), activity.getStartTime(), activity.getEndTime()))
//                    .collect(Collectors.toSet());


            // iterate through activityOccurrences and check if there is no activity with the same date and conflicting times
            for (ActivityOccurrence newOccurrence : newOccurrences) {
                List<ActivityOccurrence> sameDayExisting = existingByDay.getOrDefault(newOccurrence.getDay(), List.of());

                for (ActivityOccurrence existing : sameDayExisting) {
                    if (timesOverlap(newOccurrence.getStartTime(), newOccurrence.getEndTime(),
                            existing.getStartTime(), existing.getEndTime())) {
                        throw new ConflictException("Schedule Conflict on " + newOccurrence.getDay() + " An activity is already scheduled for this intake at that time");
                    }
                }
            }

        }

        // Add the list of occurencies to the activity
        activity.setActivityOccurrences(newOccurrences);
        activity.setCreatedAt(LocalDateTime.now());
        Activity saved = activityRepository.save(activity);
        return activityMapper.fromActivity(saved);
    }

    public ActivityResponse getActivityById(String id) {
        return activityMapper.fromActivity(findByIdOrThrow(id));
    }

    public Optional<Activity> getEntity(String id){
        return activityRepository.findById(id);
    }

    public PagedResponse<ActivityResponse> getAllActivities(int page, int size, String sortBy, String order) {
        Pageable pageable = PageRequest.of(page, size,
                order.equalsIgnoreCase("desc") ? Sort.by(sortBy).descending() : Sort.by(sortBy).ascending());

        Page<Activity> activityPage = activityRepository.findAll(pageable);

        List<ActivityResponse> content = activityPage.getContent().stream()
                .map(activityMapper::fromActivity)
                .toList();

        return PagedResponse.<ActivityResponse>builder()
                .content(content)
                .pageNumber(activityPage.getNumber())
                .pageSize(activityPage.getSize())
                .totalElements(activityPage.getTotalElements())
                .totalPages(activityPage.getTotalPages())
                .last(activityPage.isLast())
                .build();
    }


    public ActivityResponse updateActivity(String id, ActivityRequest request) {
        Activity existing = findByIdOrThrow(id);
        ActivityMapper.updateEntityFromRequest(existing, request);
        return activityMapper.fromActivity(activityRepository.save(existing));
    }

    public void deleteActivity(String id) {
        activityRepository.deleteById(id);
    }

    // ------------------ ACTIVITY LEVEL ------------------

    public ActivityLevelResponse addActivityLevel(String activityId, ActivityLevelRequest request) {
        Activity activity = findByIdOrThrow(activityId);

        ActivityLevel level = activityLevelMapper.toActivityLevel(request);
        level.setId(UUID.randomUUID().toString());

        if (activity.getActivityLevels() == null) {
            activity.setActivityLevels(new ArrayList<>());
        }

        activity.getActivityLevels().add(level);
        activityRepository.save(activity);

        return activityLevelMapper.fromActivityLevel(level);
    }

    public List<ActivityLevelResponse> getActivityLevels(String activityId) {
        Activity activity = findByIdOrThrow(activityId);
        if (activity.getActivityLevels() == null) return List.of();

        return activity.getActivityLevels().stream()
                .map(activityLevelMapper::fromActivityLevel)
                .toList();
    }

    public void deleteActivityLevel(String activityId, String levelId) {
        Activity activity = findByIdOrThrow(activityId);
        if (activity.getActivityLevels() != null) {
            activity.getActivityLevels().removeIf(l -> l.getId().equals(levelId));
            activityRepository.save(activity);
        }
    }

    // ------------------ ACTIVITY OCCURRENCE ------------------

    public void addActivityOccurrence(String activityId, ActivityOccurrence occurrence) {
        Activity activity = findByIdOrThrow(activityId);
        occurrence.setId(UUID.randomUUID().toString());

        if (activity.getActivityOccurrences() == null) {
            activity.setActivityOccurrences(new ArrayList<>());
        }

        activity.getActivityOccurrences().add(occurrence);
        activityRepository.save(activity);
    }

    public List<ActivityOccurrence> getActivityOccurrences(String activityId) {
        Activity activity = findByIdOrThrow(activityId);
        return activity.getActivityOccurrences() != null ? activity.getActivityOccurrences() : List.of();
    }

    public void deleteActivityOccurrence(String activityId, String occurrenceId) {
        Activity activity = findByIdOrThrow(activityId);
        if (activity.getActivityOccurrences() != null) {
            activity.getActivityOccurrences().removeIf(o -> o.getId().equals(occurrenceId));
            activityRepository.save(activity);
        }
    }

    // ------------------ ATTENDANCE MISSING ------------------

    public void addAttendanceMissing(String activityId, String occurrenceId, AttendanceMissing missing) {
        Activity activity = findByIdOrThrow(activityId);
        ActivityOccurrence occurrence = getOccurrenceOrThrow(activity, occurrenceId);

        if (occurrence.getAttendanceMissings() == null) {
            occurrence.setAttendanceMissings(new ArrayList<>());
        }

        missing.setId(UUID.randomUUID().toString());
        occurrence.getAttendanceMissings().add(missing);
        activityRepository.save(activity);
    }

    public List<AttendanceMissing> getAttendanceMissings(String activityId, String occurrenceId) {
        Activity activity = findByIdOrThrow(activityId);
        ActivityOccurrence occurrence = getOccurrenceOrThrow(activity, occurrenceId);
        return occurrence.getAttendanceMissings() != null ? occurrence.getAttendanceMissings() : List.of();
    }

    public void deleteAttendanceMissing(String activityId, String occurrenceId, String missingId) {
        Activity activity = findByIdOrThrow(activityId);
        ActivityOccurrence occurrence = getOccurrenceOrThrow(activity, occurrenceId);

        if (occurrence.getAttendanceMissings() != null) {
            occurrence.getAttendanceMissings().removeIf(m -> m.getId().equals(missingId));
            activityRepository.save(activity);
        }
    }

    // ------------------ INTERNAL UTILS ------------------

    private Activity findByIdOrThrow(String id) {
        return activityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Activity not found"));
    }

    private ActivityOccurrence getOccurrenceOrThrow(Activity activity, String occurrenceId) {
        return activity.getActivityOccurrences().stream()
                .filter(o -> o.getId().equals(occurrenceId))
                .findFirst()
                .orElseThrow(() -> new EntityNotFoundException("Occurrence not found"));
    }

    private List<ActivityOccurrence> generateOccurencesFromActivity(Activity activity){
        List<ActivityOccurrence> occurrences = new ArrayList<>();
        // check if this is an intake
        ActivityLevel level = activity.getActivityLevels().stream()
                .filter(x -> x.getLevel() == ActivityLevelLevels.INTAKE)
                .findFirst()
                .orElse(null);


        if(level != null && level.getLevel() == ActivityLevelLevels.INTAKE) { // This is for an intake
            // get the intake
            Intake intake = intakeService.getEntity(UUID.fromString(level.getLevelRefId().toString()))
                    .orElseThrow(() -> new EntityNotFoundException("Intake not found"));
            // get the range of dates for this intake from sessions
            StudyModeSession session = studyModeSessionService.getEntity(intake.getStudyModeSession().getId())
                    .orElseThrow(() -> new EntityNotFoundException("The Study Session for this intake is not found"));

            // get lecturer id if this is a course

            ActivityType activityType = activityTypeService.getEntity(activity.getActivityTypeId())
                    .orElseThrow(() -> new EntityNotFoundException("Activity Type not found !"));

            UUID lecturerId = null;
            if(activityType.getName().equals("LECTURE"))
                lecturerId = activity.getLecturerId();
            // iterate through start date and end date and add to the list everytime we are in the range
            LocalDate start = activity.getStartDay();
            while(!start.isAfter(activity.getEndDay())){
                DayOfWeek day = start.getDayOfWeek();
                if(day.getValue() >= session.getStartingDay().getValue() && day.getValue() <= session.getEndingDay().getValue())
                    occurrences.add(new ActivityOccurrence(
                            UUID.randomUUID().toString(),
                            start,
                            activity.getStartTime(),
                            activity.getEndTime(),
                            lecturerId,
                            false,
                            false,
                            null
                    ));
                start = start.plusDays(1);
            }
        }else{
            // else just iterate through start to end dates and add
            LocalDate start = activity.getStartDay();
            while(!start.isAfter(activity.getEndDay())){

                occurrences.add(new ActivityOccurrence(
                        UUID.randomUUID().toString(),
                        start,
                        activity.getStartTime(),
                        activity.getEndTime(),
                        null,
                        false,
                        false,
                        null
                ));
                start = start.plusDays(1);
            }
        }
        return occurrences;
    }

    boolean timesOverlap(LocalTime start1, LocalTime end1, LocalTime start2, LocalTime end2) {
        return start1.isBefore(end2) && start2.isBefore(end1);
    }

    public Optional<Activity> getEntityOptionExistByActivityId(String id){
        return activityRepository.findByActivityTypeId(id);
    }
}


package rw.ac.ilpd.academicservice.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import rw.ac.ilpd.academicservice.exception.EntityAlreadyExists;
import rw.ac.ilpd.academicservice.integration.client.LocationClient;
import rw.ac.ilpd.academicservice.mapper.IntakeMapper;
import rw.ac.ilpd.academicservice.model.nosql.document.DeliberationDistinctionGroup;
import rw.ac.ilpd.academicservice.model.nosql.document.DeliberationRuleGroup;
import rw.ac.ilpd.academicservice.model.sql.Curriculum;
import rw.ac.ilpd.academicservice.model.sql.InstitutionShortCourseSponsor;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.model.sql.Program;
import rw.ac.ilpd.academicservice.model.sql.StudyModeSession;
import rw.ac.ilpd.academicservice.repository.sql.*;
import rw.ac.ilpd.academicservice.scheduler.IntakeScheduler;
import rw.ac.ilpd.sharedlibrary.dto.intake.ApplicationRequiredDocIntakeResponse;
import rw.ac.ilpd.sharedlibrary.dto.intake.IntakeRequest;
import rw.ac.ilpd.sharedlibrary.dto.intake.IntakeResponse;
import rw.ac.ilpd.sharedlibrary.enums.IntakeStatus;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Service class for managing Intake entities.
 * Provides business logic for creating, retrieving, updating, and deleting intakes.
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class IntakeService
{
    private final IntakeRepository intakeRepository;
    private final ProgramService programService;
    private final StudyModeSessionService studyModeSessionService;
    private final CurriculumService curriculumService;
    private final InstitutionShortCourseSponsorService institutionService;
    private final DeliberationRuleGroupService deliberationRuleGroupService;
    private final DeliberationDistinctionGroupService deliberationDistinctionGroupService;
    private final IntakeMapper intakeMapper;
    private final LocationClient locationClient;
    private final IntakeApplicationRequiredDocRepository intakeApplicationRequiredDocRepository;
    private final IntakeScheduler intakeScheduler;
    /**
     * Creates a new intake based on the provided request.
     *
     * @param request The IntakeRequest containing intake details.
     * @return IntakeResponse with the created intake details.
     * @throws EntityNotFoundException() if referenced entities are not found.
     * @throws IllegalArgumentException  if the request is invalid or violates constraints.
     */
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    public IntakeResponse createIntake(IntakeRequest request)
    {  Program program = getValidatedProgram(request.getProgramId());
        if(program.getProgramType().getName().equalsIgnoreCase("PROGRAM")) {
            return saveOrUpdateProgramIntake(null, request, program);
        }else  {
            return saveOrUpdateCLEIntake(null, request, program);
        }
    }


    public IntakeResponse updateIntake(String id,IntakeRequest request){
        Program program = getValidatedProgram(request.getProgramId());
        if(program.getProgramType().getName().equalsIgnoreCase("PROGRAM")) {
            return saveOrUpdateProgramIntake(id, request, program);
        }else  {
            return saveOrUpdateCLEIntake(id, request, program);
        }
    }
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    private IntakeResponse saveOrUpdateProgramIntake(String intakeId,IntakeRequest request,Program program){
    log.info("Creating new intake with name: {}", request.getName());
    // validate the location
     validateLocation(request.getLocationId());
    // Check for duplicate intake name within program
        if (intakeId == null) {
            intakeExistByCreatingIntake(request, program);
        } else {
            intakeNameExistByUpdatingIntake(intakeId, request, program);
        }
        log.info("Study Session mode before {}",request.getStudyModeSessionId());
//  Fetch Study session mode
    StudyModeSession studyModeSession = getValidatedStudyModeSession(request.getStudyModeSessionId());
    log.info("Study Session mode after {}",studyModeSession.getId());
//  Validate curriculum
    Curriculum curriculum =getValidatedCurriculum(request.getCurriculumId());
//  validate deliberation rule group
    String deliberationRuleGroup=getValidatedDeliberationRuleGroup(request.getDeliberationGroupId());
//    Validate distinction group
    String distinctionGroup=getValidatedDistinctionGroup(request.getDeliberationDistinctionGrpId());
    Intake intake;
    // Map request to entity for cre
    if(intakeId!=null){
        Intake intakeToUpdate = findIntakeById(intakeId);
        intake=intakeMapper.toIntakeUpdate(intakeToUpdate,request, program, studyModeSession, curriculum,deliberationRuleGroup,distinctionGroup);
    }else{
            intake=intakeMapper.toIntake(request, program, studyModeSession, curriculum,deliberationRuleGroup,distinctionGroup);
    }
    if (intake == null)
    {
        log.error("Failed to map IntakeRequest to Intake entity");
        throw new IllegalArgumentException("Failed to create intake");
    }

    // Save the entity
   return saveIntake(intake);
}

//Control Cle intakes
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    private  IntakeResponse saveOrUpdateCLEIntake(String intakeId,IntakeRequest request,Program program){
//        Check location
        validateLocation(request.getLocationId());
        // Check for duplicate intake name within program
        if (intakeId == null) {
            intakeExistByCreatingIntake(request, program);
        } else {
            intakeNameExistByUpdatingIntake(intakeId, request, program);
        }
        InstitutionShortCourseSponsor institution=new InstitutionShortCourseSponsor();
        if(request.getInstitutionId().isBlank()){
            throw new IllegalArgumentException("Institution Id is blank");
        }else {
            institution=institutionService.getEntity(UUID
                            .fromString(request.getInstitutionId()))
                    .orElseThrow(() ->
                    {
                        log.error("Institution not found for ID: {}", request.getInstitutionId());
                        return new EntityNotFoundException("Institution not found with ID: " + request.getInstitutionId());
                    });
        }
        Intake mappedIntake;
        if(intakeId==null){
            mappedIntake=intakeMapper.toCleIntake(request,program,institution);
        }else {
            Intake intake=findIntakeById(intakeId);
            mappedIntake=intakeMapper.toCleIntakeUpdate(intake,request,program,institution);
        }
//       create or update intake
        return saveIntake(mappedIntake);
    }
private void intakeExistByCreatingIntake(IntakeRequest request,Program program){
    if (intakeRepository.existsByNameAndProgram(request.getName(), program))
    {
        log.warn("Intake with name '{}' already exists for programId: {}"
                , request.getName(), request.getProgramId());
        throw new EntityAlreadyExists("Intake with name '" + request.getName()
                + "' already exists for this program");
    }
}
    private void intakeNameExistByUpdatingIntake(String intakeId,IntakeRequest request,Program program){
        if (intakeRepository.existsByNameAndProgramAndIdNot(request.getName(), program,UUID.fromString(intakeId)))
        {
            log.warn("Intake with name '{}' already exists for programId: {}"
                    , request.getName(), request.getProgramId());
            throw new EntityAlreadyExists("Intake with name '" + request.getName()
                    + "' already exists for this program");
        }
    }
    /**
     * Retrieves an intake by its ID.
     *
     * @param id The UUID of the intake.
     * @return IntakeResponse containing the intake details.
     * @throws EntityNotFoundException() if the intake is not found.
     */
    @Transactional(readOnly = true)
    public IntakeResponse getIntakeById(String id)
    {
        log.info("Retrieving intake with ID: {}", id);

        // Validate ID format
        UUID uuid;
        try
        {
            uuid = UUID.fromString(id);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Invalid UUID format for ID: {}", id);
            throw new IllegalArgumentException("Invalid intake ID format");
        }

        // Find the intake
        Intake intake = intakeRepository.findById(uuid)
                .orElseThrow(() ->
                {
                    log.error("Intake not found for ID: {}", id);
                    return new EntityNotFoundException("Intake not found with ID: " + id);
                });

        // Map to response DTO
        IntakeResponse response = intakeMapper.toIntakeResponse(intake, getApplicationRequiredDocs(intake));
        if (response == null)
        {
            log.error("Failed to map Intake to IntakeResponse for ID: {}", id);
            throw new IllegalStateException("Failed to map intake to response");
        }

        log.debug("Successfully retrieved intake with ID: {}", id);
        return response;
    }

    /**
     * Retrieves all intakes with pagination and sorting support.
     *
     * @return Page of IntakeResponse containing the list of intakes.
     */
    @Transactional(readOnly = true)
    public Page<IntakeResponse> findAllIntakes(int page, int size, String sortBy, String sortDir)
    {
        log.info("Retrieving possible session couples with page: {}, size: {}, sortBy: {}, sortDir: {}",
                page, size, sortBy, sortDir);

        Sort sort = sortDir.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending()
                : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);

        // Fetch paginated intakes
        Page<Intake> intakes = intakeRepository.findAll(pageable);
        log.debug("Retrieved {} intakes for page: {}", intakes.getTotalElements(), pageable);

        // Map entities to response DTOs
        List<IntakeResponse> responses = intakes.getContent().stream()
                .map(i -> intakeMapper.toIntakeResponse(i, getApplicationRequiredDocs(i)))
                .filter(response ->
                {
                    if (response == null)
                    {
                        log.warn("Failed to map an Intake entity to IntakeResponse");
                        return false;
                    }
                    return true;
                })
                .collect(Collectors.toList());

        log.info("Successfully retrieved {} intakes for page: {}", responses.size(), pageable);
        return new PageImpl<>(responses, pageable, intakes.getTotalElements());
    }


    /**
     * Deletes an intake by performing a hard delete.
     *
     * @param id The UUID of the intake to delete.
     * @throws EntityNotFoundException if the intake is not found.
     */
    @Transactional
    public void deleteIntake(String id)
    {
        log.info("Deleting intake with ID: {}", id);

        // Validate ID format
        UUID uuid;
        try
        {
            uuid = UUID.fromString(id);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Invalid UUID format for ID: {}", id);
            throw new IllegalArgumentException("Invalid intake ID format");
        }

        // Check if intake exists
        if (!intakeRepository.existsById(uuid))
        {
            log.error("Intake not found for ID: {}", id);
            throw new EntityNotFoundException("Intake not found with ID: " + id);
        }

        // Perform hard deletion
        intakeRepository.deleteById(uuid);
        log.info("Successfully deleted intake with ID: {}", id);
    }

    /**
     * Updates the status of an intake to OPEN or CLOSED.
     *
     * @param id     The UUID of the intake to update.
     * @param status The new status (OPEN or CLOSED).
     * @return IntakeResponse with the updated intake details.
     * @throws EntityNotFoundException  if the intake is not found.
     * @throws IllegalArgumentException if the status is invalid.
     */
    @Transactional
    public IntakeResponse updateIntakeStatus(String id, String status)
    {
        log.info("Updating status of intake with ID: {} to {}", id, status);

        // Validate ID format
        UUID uuid;
        try
        {
            uuid = UUID.fromString(id);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Invalid UUID format for ID: {}", id);
            throw new IllegalArgumentException("Invalid intake ID format");
        }

        // Validate status
        IntakeStatus intakeStatus;
        try
        {
            intakeStatus = IntakeStatus.valueOf(status);
        }
        catch (IllegalArgumentException e)
        {
            log.error("Invalid IntakeStatus: {}", status);
            throw new IllegalArgumentException("Invalid status value: " + status);
        }

        // Find the intake
        Intake intake = intakeRepository.findById(uuid)
                .orElseThrow(() ->
                {
                    log.error("Intake not found for ID: {}", id);
                    return new EntityNotFoundException("Intake not found with ID: " + id);
                });

        // Update status
        intake.setStatus(intakeStatus);
        Intake savedIntake = intakeRepository.save(intake);
        log.debug("Successfully updated intake status with ID: {} to {}", id, status);

        // Map to response DTO
        IntakeResponse response = intakeMapper.toIntakeResponse(savedIntake, getApplicationRequiredDocs(savedIntake));
        if (response == null)
        {
            log.error("Failed to map updated Intake to IntakeResponse for ID: {}", id);
            throw new IllegalStateException("Failed to map updated intake to response");
        }

        log.info("Successfully updated intake status with ID: {} to {}", id, status);
        return response;
    }

    /**
     * Utility function to return an intake by id to be used by other service classes
     */
    @Transactional(readOnly = true)
    public Optional<Intake> getEntity(UUID id)
    {
        return intakeRepository.findById(id);
    }

    /**
     * Utility function to return if an intake exists by id
     */
    @Transactional(readOnly = true)
    public boolean intakeExistsById(UUID id)
    {
        return intakeRepository.existsById(id);
    }

    @Transactional(readOnly = true)
    private List<ApplicationRequiredDocIntakeResponse> getApplicationRequiredDocs(Intake intake)
    {
        return intakeApplicationRequiredDocRepository.findByIntake(getEntity(intake.getId()).get()).stream()
                        .map(doc ->
                                new ApplicationRequiredDocIntakeResponse(doc.getId(),
                                        doc.getDocumentRequiredName().getId(), doc.getIsRequired())).toList();
    }

    /*Reusable intake methods*/
    @Transactional(readOnly = false,rollbackFor = Exception.class)
    private IntakeResponse saveIntake(Intake intake){
        Intake savedIntake = intakeRepository.save(intake);
        log.debug("Successfully saved intake with ID: {}", savedIntake.getId());
//        add intake date schedulers
        intakeScheduler.intakeScheduler(intake);
        // Map to response DTO
        IntakeResponse response = intakeMapper.toIntakeResponse(savedIntake, getApplicationRequiredDocs(intake));
        if (response == null)
        {
            log.error("Failed to map saved Intake to IntakeResponse");
            throw new IllegalStateException("Failed to map saved intake to response");
        }

        log.info("Successfully created intake with ID: {}", response.getId());
        return response;
    }
    /* ========= Reusable validation methods ========= */
    @Transactional(readOnly = true)
    private Program getValidatedProgram(String programId) {
        return programService.getEntity(UUID.fromString(programId))
                .orElseThrow(() -> new EntityNotFoundException("Program not found with ID: " + programId));
    }
    @Transactional(readOnly = true)
    private void validateLocation(String locationId) {

        if (locationClient.get(locationId) == null) {
            log.error("Location not found for ID: {}", locationId);
            throw new EntityNotFoundException("Location not found with ID: " + locationId);
        }
    }
    @Transactional(readOnly = true)
    private StudyModeSession getValidatedStudyModeSession(String sessionId) {
        return studyModeSessionService.getEntity(UUID.fromString(sessionId))
                .orElseThrow(() -> {
                    log.error("Study Mode Session not found for ID: {}", sessionId);
                    return new EntityNotFoundException("Study Mode Session not found");
                });
    }
    @Transactional(readOnly = true)
    private Curriculum getValidatedCurriculum(String curriculumId) {
        return curriculumService.getEntity(UUID.fromString(curriculumId))
                .orElseThrow(() ->
                {
                    log.error("Curriculum not found for ID: {}", curriculumId);
                    return new EntityNotFoundException("Curriculum not found ");
                });
    }
    @Transactional(readOnly = true)
    private String getValidatedDeliberationRuleGroup(String deliberationRuleGroupId) {
        return deliberationRuleGroupService.getEntity(deliberationRuleGroupId)
                .map(DeliberationRuleGroup::getId).orElse(null);
    }
    @Transactional(readOnly = true)
    private String getValidatedDistinctionGroup(String distinctionGroupId) {
        return deliberationDistinctionGroupService.getById(distinctionGroupId)
                .map(DeliberationDistinctionGroup::getId).orElse(null);
    }
    @Transactional(readOnly = true)
    private Intake findIntakeById(String id) {
        return getEntity(UUID.fromString(id)).orElseThrow(()->{
            log.warn("Intake with id '{}' does not exist", id);
            return new EntityNotFoundException("Intake  does not exist");
        });
    }
}
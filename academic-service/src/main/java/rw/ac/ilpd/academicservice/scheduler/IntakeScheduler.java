package rw.ac.ilpd.academicservice.scheduler;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.model.sql.Intake;
import rw.ac.ilpd.academicservice.repository.sql.IntakeRepository;
import rw.ac.ilpd.academicservice.scheduler.builder.TaskBuilder;
import rw.ac.ilpd.sharedlibrary.enums.IntakeStatus;

import java.time.LocalDateTime;
@Component
@RequiredArgsConstructor
@Slf4j
public class IntakeScheduler extends TaskBuilder {
    private final IntakeRepository intakeRepository;
    public String APPLICATION_CLOSING_SCHEDULER = "APPLICATION_DEADLINE_SCHEDULER";
    public String APPLICATION_OPENING_SCHEDULER = "APPLICATION_OPENING_SCHEDULER";
    public String INTAKE_STARTING_DATE_SCHEDULER = "INTAKE_STARTING_DATE_SCHEDULER";
    public String INTAKE_ENDING_DATE_SCHEDULER = "INTAKE_ENDING_DATE_SCHEDULER";
    public String INTAKE_GRADUATION_DATE_SCHEDULER = "INTAKE_GRADUATION_DATE_SCHEDULER";

    @Async
    public void intakeScheduler(Intake intake) {
//        Schedule opening application
        if(intake.getApplicationOpeningDate()!=null&&intake.getApplicationOpeningDate().isAfter(LocalDateTime.now())) {
            intakeSchedulers(APPLICATION_OPENING_SCHEDULER + "-" + intake.getId(), intake, IntakeStatus.OPEN, intake.getApplicationOpeningDate());
        }
//        Schedule closing application
        if(intake.getApplicationClosingDate()!=null&&intake.getApplicationClosingDate().isAfter(LocalDateTime.now())) {
            intakeSchedulers(APPLICATION_CLOSING_SCHEDULER+"-"+intake.getId(),intake,IntakeStatus.CLOSED,intake.getApplicationClosingDate());
        }
//        Schedule opening application
        if(intake.getStartDate()!=null&&intake.getStartDate().isAfter(LocalDateTime.now())) {
            intakeSchedulers(INTAKE_STARTING_DATE_SCHEDULER+"-"+intake.getId(),intake,IntakeStatus.CLOSED,intake.getStartDate());
        }
//        Schedule opening application
        if(intake.getEndDate()!=null&&intake.getEndDate().isAfter(LocalDateTime.now())) {
            intakeSchedulers(INTAKE_ENDING_DATE_SCHEDULER+"-"+intake.getId(),intake,IntakeStatus.CLOSED,intake.getEndDate());
        }
//        Schedule opening application
        if(intake.getGraduationDate()!=null&&intake.getGraduationDate().isAfter(LocalDateTime.now())) {
            intakeSchedulers(INTAKE_GRADUATION_DATE_SCHEDULER+"-"+intake.getId(),intake,IntakeStatus.CLOSED,intake.getGraduationDate());
        }
    }
    private void intakeSchedulers(String scheduleName,Intake intake,IntakeStatus intakeStatus,LocalDateTime date) {
        log.info("Intake scheduler of {} has status of {} is registered",scheduleName,intakeStatus);
        task(scheduleName,()->{
            log.info("Intake scheduler started");
            intake.setStatus(intakeStatus);
           Intake intake1= intakeRepository.save(intake);
            log.info("Intake having id :: {} and status of :: {} status has changed to :: {} and scheduler finished at :: {}",intake1.getId(),intake.getStatus(),intake1.getStatus(),LocalDateTime.now());
        }).onceAt(date.getMonthValue(),date.getDayOfMonth(),date.getHour(),date.getMinute());
    }
}

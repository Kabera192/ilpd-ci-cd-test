package rw.ac.ilpd.academicservice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rw.ac.ilpd.academicservice.model.nosql.document.ActivityType;
import rw.ac.ilpd.academicservice.repository.nosql.ActivityTypeRepository;

@Configuration
@RequiredArgsConstructor
public class InitActivityType {
    private final ActivityTypeRepository activityTypeRepository;
    @Bean
    public CommandLineRunner initActivityTypeBean(){
        return args -> {
            if(!activityTypeRepository.existsByName("LECTURE")){
                activityTypeRepository.save(ActivityType.builder().name("LECTURE")
                                .isDeleted(false)
                        .description("This activity is for lectures happening in class").build());
            }
            if(!activityTypeRepository.existsByName("LAB")){
                activityTypeRepository.save(ActivityType.builder().name("LAB")
                                .isDeleted(false)
                        .description("This activity is for labs").build());
            }
            if(!activityTypeRepository.existsByName("SEMINAR")){
                activityTypeRepository.save(ActivityType.builder().name("SEMINAR")
                                .isDeleted(false)
                        .description("This activity is for seminars").build());
            }
        };
    }
}

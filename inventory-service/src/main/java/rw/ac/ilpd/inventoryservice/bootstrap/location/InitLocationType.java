package rw.ac.ilpd.inventoryservice.bootstrap.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.nosql.document.LocationType;
import rw.ac.ilpd.inventoryservice.repository.nosql.LocationTypeRepository;

@Component("InitLocationType")
@RequiredArgsConstructor
@Slf4j
public class InitLocationType {

    private final LocationTypeRepository locationTypeRepository;

    @Bean
    public CommandLineRunner initTable() {
        return args -> {
            createIfNotExists("COUNTRY");
            createIfNotExists("SECTOR");
            createIfNotExists("DISTRICT");
            createIfNotExists("BLOCK");
            createIfNotExists("LOCATION");
            createIfNotExists("CAMPUS");
        };
    }

    private void createIfNotExists(String name) {
        log.info("🚀 Application started — initializing default location type...");
        if (!locationTypeRepository.existsByName(name)) {
            LocationType locationType = LocationType.builder()
                    .name(name)
                    .deleteStatus(false)
                    .build();
            locationTypeRepository.save(locationType);
        }
    }
}

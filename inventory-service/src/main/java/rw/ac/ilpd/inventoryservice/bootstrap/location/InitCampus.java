package rw.ac.ilpd.inventoryservice.bootstrap.location;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.inventoryservice.model.nosql.document.Location;
import rw.ac.ilpd.inventoryservice.service.LocationService;

import java.math.BigDecimal;
import java.util.List;

@Component()
@Slf4j
@RequiredArgsConstructor
@DependsOn("InitLocationType")
public class InitCampus implements CommandLineRunner {
    private final LocationService locationService;
    private final List<Campus> campusList=List.of(
            new Campus("Nyanza Campus","", BigDecimal.valueOf(0.212_121),BigDecimal.valueOf(0.3223212)),
            new Campus("Kigali Campus","", BigDecimal.valueOf(0.212_121),BigDecimal.valueOf(0.3223212)),
            new Campus("Musanze Campus","", BigDecimal.valueOf(0.212_121),BigDecimal.valueOf(0.3223212)),
            new Campus("Huye Campus","", BigDecimal.valueOf(0.212_121),BigDecimal.valueOf(0.3223212))
    );
    @Override
    public void run(String... args) throws Exception {
        initCampus();
    }
    private void initCampus(){
        log.info("🚀 Application started — initializing campus...");
        Location location = new Location();
        int savedLocations=locationService.saveInitialListOfCampus("CAMPUS",campusList);
        log.info("🚀 Total — initializing default campus ::: {}", savedLocations);
    }
    public record Campus(String name, String location, BigDecimal latitude, BigDecimal longitude) {}
}

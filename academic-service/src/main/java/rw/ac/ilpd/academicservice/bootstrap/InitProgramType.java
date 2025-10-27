package rw.ac.ilpd.academicservice.bootstrap;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import rw.ac.ilpd.academicservice.service.ProgramTypeService;
import rw.ac.ilpd.sharedlibrary.dto.programtype.ProgramTypeRequest;

import java.util.List;
@Slf4j
@Component
@RequiredArgsConstructor
public class InitProgramType  implements CommandLineRunner {
    private final ProgramTypeService programTypeService;
    private final List<ProgramTypeRequest> programTypeRequests=List.of(
            new ProgramTypeRequest("PROGRAM"),
            new ProgramTypeRequest("CONTINUOUS LEGAL EDUCATION")

    ) ;
    @Override
    public void run(String... args) throws Exception {
        initDefaultProgramType();

    }
    private void initDefaultProgramType(){
        log.info("🚀 Application started — initializing default program type... {}",programTypeRequests.size());
        long savedProgramTypes=programTypeService.createListOfInitProgramTypes(programTypeRequests);
        log.info("🚀 Application started — Count saved default program new type  {}",savedProgramTypes);
    }
}

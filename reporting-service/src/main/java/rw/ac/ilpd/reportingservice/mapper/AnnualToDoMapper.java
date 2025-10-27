package rw.ac.ilpd.reportingservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import rw.ac.ilpd.reportingservice.model.nosql.document.AnnualToDo;
import rw.ac.ilpd.sharedlibrary.dto.planning.AnnualToDoRequest;
import rw.ac.ilpd.sharedlibrary.dto.planning.AnnualToDoResponse;
import rw.ac.ilpd.sharedlibrary.enums.ToDoStatus;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

/**
 * Author: Michel Igiraneza
 * Created: 2025-08-14
 */
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE,
        imports = {UUID.class, ToDoStatus.class, LocalDateTime.class, ChronoUnit.class})
public interface AnnualToDoMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "isDeleted", constant = "false")
    @Mapping(target = "status", expression = "java(ToDoStatus.fromString(request.getStatus()))")
    @Mapping(target = "unitId", expression = "java(UUID.fromString(request.getUnitId()))")
    AnnualToDo toEntity(AnnualToDoRequest request);

    @Mapping(target = "status", expression = "java(entity.getStatus().name())")
    @Mapping(target = "unitId", expression = "java(entity.getUnitId().toString())")
    @Mapping(target = "createdAt", expression = "java(entity.getCreatedAt() != null ? entity.getCreatedAt().toString() : null)")
    @Mapping(target = "updatedAt", expression = "java(entity.getUpdatedAt() != null ? entity.getUpdatedAt().toString() : null)")
    @Mapping(target = "durationInDays", expression = "java(calculateDurationInDays(entity.getStartDate(), entity.getEndDate()))")
    @Mapping(target = "isOverdue", expression = "java(isOverdue(entity.getEndDate(), entity.getStatus()))")
    AnnualToDoResponse toResponse(AnnualToDo entity);

    List<AnnualToDoResponse> toResponseList(List<AnnualToDo> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "requestId", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdBy", ignore = true)
    @Mapping(target = "updatedBy", ignore = true)
    @Mapping(target = "deleted", ignore = true)
    @Mapping(target = "status", expression = "java(ToDoStatus.fromString(request.getStatus()))")
    @Mapping(target = "unitId", expression = "java(UUID.fromString(request.getUnitId()))")
    void updateEntityFromRequest(AnnualToDoRequest request, @MappingTarget AnnualToDo entity);

    default Long calculateDurationInDays(LocalDate startDate, LocalDate endDate) {
        if (startDate != null && endDate != null) {
            return  ChronoUnit.DAYS.between(startDate, endDate) + 1;
        }
        return null;
    }

    default Boolean isOverdue(LocalDate endDate, ToDoStatus status) {
        if (endDate != null && status != ToDoStatus.COMPLETED && status != ToDoStatus.CANCELLED) {
            return endDate.isBefore(LocalDate.now());
        }
        return false;
    }
}
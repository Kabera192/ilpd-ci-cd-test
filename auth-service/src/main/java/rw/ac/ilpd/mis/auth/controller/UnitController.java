package rw.ac.ilpd.mis.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.mis.auth.service.UnitService;
import rw.ac.ilpd.mis.shared.api.UnitApi;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.privilege.auth.UnitPriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.dto.unit.Unit;
import rw.ac.ilpd.mis.shared.util.helpers.MisResponse;
import rw.ac.ilpd.mis.shared.util.helpers.PagedResponse;

import java.util.List;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 24/07/2024
 */

@Tag(
        name = "Units",
        description = "Unit management APIs (list, fetch, search, create, update, delete)"
)
@RestController
@RequestMapping(MisConfig.AUTH_PATH + UnitPriv.GET_UNITS_PATH)
public class UnitController implements UnitApi {

    @Autowired
    UnitService unitService;

    private static final Logger logger = LoggerFactory.getLogger(UnitController.class);

    @Secured({SuperPrivilege.SUPER_ADMIN, UnitPriv.GET_UNITS})
    @Operation(
            summary = "List units (paged)",
            description = "Returns a paginated list of units.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "page",  in = ParameterIn.QUERY, description = "Page index (0-based)", schema = @Schema(implementation = Integer.class), example = "0"),
                    @Parameter(name = "size",  in = ParameterIn.QUERY, description = "Page size", schema = @Schema(implementation = Integer.class), example = "20"),
                    @Parameter(name = "sortBy",in = ParameterIn.QUERY, description = "Sort property (e.g., `id`, `name`)", schema = @Schema(implementation = String.class), example = "id")
            }
    )
    @ApiResponse(responseCode = "200", description = "Units queried successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "404", description = "No units found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "500", description = "Server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getAllUnits(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size,
                                         @RequestParam(defaultValue = "id") String sortBy){
        try{
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<Unit> unitPage = unitService.getUnits(pageable);
            if (unitPage.isEmpty()) {
                logger.info("No units found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MisResponse(false, "No units found"));
            }
            logger.info("Get unitList; size={}", unitPage.getTotalElements());
            PagedResponse response = new PagedResponse(unitPage);
            return ResponseEntity.ok().body(new MisResponse<>(true, "Units queried successfully.", response));
        }catch (Exception e){
            logger.error("Units failed to find : {}", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Units users failed: "+ e.getClass().getSimpleName()));
        }
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, UnitPriv.GET_UNIT})
    @Operation(
            summary = "Get unit by ID",
            description = "Returns a unit by its identifier.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "unitId", in = ParameterIn.PATH, required = true, description = "Unit ID", schema = @Schema(implementation = String.class))
    )
    @ApiResponse(responseCode = "200", description = "Unit found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class)))
    @ApiResponse(responseCode = "500", description = "Error fetching unit",
            content = @Content(mediaType = "application/json"))
    @GetMapping(path = UnitPriv.GET_UNIT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getUnit(@PathVariable String unitId) {
        try{
            Unit unit = unitService.getUnit(unitId);
            if (unit == null) {
                logger.info("Could not get unit for unit id {}", unitId);
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MisResponse<>(false,"Could not get unit for unit id : "+ unitId));
            }
            logger.info("Get unit; context={}", unit);
            return ResponseEntity.status(HttpStatus.OK).body(new MisResponse<>(true, "Unit found successfully", unit));
        }catch (Exception e){
            logger.error("Units failed to find", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MisResponse<>(false, "Units failed to find: "+ e.getClass().getSimpleName(), unitId));
        }

    }

    @Secured({SuperPrivilege.SUPER_ADMIN, UnitPriv.GET_UNIT_BY_NAME})
    @Operation(
            summary = "Get unit by name",
            description = "Returns a unit by its unique name.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "unitName", in = ParameterIn.QUERY, required = true, description = "Unit name", schema = @Schema(implementation = String.class))
    )
    @ApiResponse(responseCode = "200", description = "Unit found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Unit.class)))
    @ApiResponse(responseCode = "500", description = "Error fetching unit",
            content = @Content(mediaType = "application/json"))
    @GetMapping(path = UnitPriv.GET_UNIT_BY_NAME_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getUnitByName(@PathVariable String unitName) {
        try{
            Unit unit = unitService.getUnitByName(unitName);
            if (unit == null) {
                logger.info("Could not get unit for unit id {}", unitName);
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MisResponse<>(false, "Could not get unit for unit name : "+ unitName));
            }
            logger.info("Get unit; context={}", unit);
            return ResponseEntity.status(HttpStatus.OK).body(new MisResponse<>(true, "Unit found by name", unit));
        }catch (Exception e){
            logger.error("Units failed to find", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MisResponse<>(false, "Units failed to find: "+ e.getClass().getSimpleName(), unitName));
        }

    }

    @Secured({SuperPrivilege.SUPER_ADMIN, UnitPriv.CREATE_UNIT})
    @Operation(
            summary = "Create unit",
            description = "Creates a new unit.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Unit.class)
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Created",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Creation failed",
            content = @Content(mediaType = "text/plain"))
    @PostMapping(path = UnitPriv.CREATE_UNIT_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> createUnit(@org.springframework.web.bind.annotation.RequestBody Unit body) {
        try {
            Unit unit = unitService.createUnit(body);
            if (unit != null) {
                logger.info("Unit created successful; context={}", body);
                return ResponseEntity.status(HttpStatus.CREATED).body(new MisResponse<>(true, "INSERTED UNIT SUCCESSFULLY", unit));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body( new MisResponse<>(false, "INSERT UNIT FAILED", body));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MisResponse<>(false, "INSERT UNIT FAILED: " +  e.getClass().getSimpleName(), body));
        }
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, UnitPriv.EDIT_UNIT})
    @Operation(
            summary = "Update unit",
            description = "Updates an existing unit by ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "unitId", in = ParameterIn.PATH, required = true, description = "Unit ID"),
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Unit.class))
            )
    )
    @ApiResponse(responseCode = "200", description = "Updated",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Update failed",
            content = @Content(mediaType = "text/plain"))
    @PutMapping(path = UnitPriv.EDIT_UNIT_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> updateUnit(@PathVariable String unitId,
                                        @org.springframework.web.bind.annotation.RequestBody Unit body) {

        try{
            Unit unit = unitService.updateUnit(unitId, body);
            if (unit != null) {
                logger.info("Unit updated successful; context={}", body);
                return ResponseEntity.status (HttpStatus.OK).body( new MisResponse<>( true, "UPDATED UNIT SUCCESSFULLY", unit ));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MisResponse<>(false, "UPDATED UNIT failure", body));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new MisResponse<>(false,"UPDATE UNIT FAILED: "+ e.getClass().getSimpleName(), body));
        }

    }

    @Secured({SuperPrivilege.SUPER_ADMIN, UnitPriv.DELETE_UNIT})
    @Operation(
            summary = "Delete unit",
            description = "Deletes a unit by ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "unitId", in = ParameterIn.PATH, required = true, description = "Unit ID")
    )
    @ApiResponse(responseCode = "200", description = "Deleted",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Delete failed",
            content = @Content(mediaType = "text/plain"))
    @GetMapping(path = UnitPriv.DELETE_UNIT_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> deleteUnit(String unitId) {
        try{
            if (unitService.deleteUnit(unitId)) {
                logger.info("Delete Unit successful");
                return ResponseEntity.status(HttpStatus.OK).body(new MisResponse<>(true, "DELETED Unit : " + unitId));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MisResponse<>(false, "DELETED UNIT failed", unitId));
        }catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body( new MisResponse<>(false, "DELETE FAILED FOR Unit: " + e.getClass().getSimpleName(),  unitId ));
        }

    }
}

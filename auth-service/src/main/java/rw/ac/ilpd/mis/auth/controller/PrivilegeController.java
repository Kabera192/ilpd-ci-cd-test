package rw.ac.ilpd.mis.auth.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import rw.ac.ilpd.mis.auth.service.PrivilegeService;
import rw.ac.ilpd.mis.shared.api.PrivilegeApi;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.privilege.auth.PrivilegePriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.dto.user.Privilege;
import rw.ac.ilpd.mis.shared.util.helpers.MisResponse;
import rw.ac.ilpd.mis.shared.util.helpers.PagedResponse;

import java.util.List;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 24/07/2024
 */


/**
 * Privilege management endpoints.
 */
@Tag(name = "Privileges", description = "Privilege management APIs (list, fetch, search, create, update, delete)")
@RestController
@RequestMapping(MisConfig.AUTH_PATH + PrivilegePriv.GET_PRIVILEGES_PATH)
public class PrivilegeController implements PrivilegeApi {

    @Autowired
    PrivilegeService privilegeService;

    private static final Logger logger = LoggerFactory.getLogger(rw.ac.ilpd.mis.auth.controller.PrivilegeController.class);

    /** List all privileges (paged). */
    @Secured({SuperPrivilege.SUPER_ADMIN, PrivilegePriv.GET_PRIVILEGES})
    @Operation(
            summary = "List privileges (paged)",
            description = "Returns a paginated list of privileges.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "page",  in = ParameterIn.QUERY, description = "Page index (0-based)", schema = @Schema(implementation = Integer.class), example = "0"),
                    @Parameter(name = "size",  in = ParameterIn.QUERY, description = "Page size", schema = @Schema(implementation = Integer.class), example = "20"),
                    @Parameter(name = "sortBy",in = ParameterIn.QUERY, description = "Sort property (e.g., `id`, `name`)", schema = @Schema(implementation = String.class), example = "id")
            }
    )
    @ApiResponse(responseCode = "200", description = "Privileges queried successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "404", description = "No privileges found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getAllPrivileges(@RequestParam(defaultValue = "0") int page,
                                              @RequestParam(defaultValue = "20") int size,
                                              @RequestParam(defaultValue = "id") String sortBy){
        Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
        Page<Privilege> privilegePage = privilegeService.getPrivileges(pageable);
        if (privilegePage.isEmpty()) {
            logger.info("No privilegeList found.");
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        logger.info("Get privilegeList; size={}", privilegePage.getTotalElements());
        PagedResponse response = new PagedResponse(privilegePage);
        return ResponseEntity.ok().body(new MisResponse<>(true, "Privileges queried successfully.", response));
    }

    /** Get a privilege by ID. */
    @Secured({SuperPrivilege.SUPER_ADMIN, PrivilegePriv.GET_PRIVILEGE})
    @Operation(
            summary = "Get privilege by ID",
            description = "Returns a privilege by its identifier.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "privilegeId", in = ParameterIn.PATH, required = true, description = "Privilege ID", schema = @Schema(implementation = String.class))
    )
    @ApiResponse(responseCode = "200", description = "Privilege found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Privilege.class)))
    @ApiResponse(responseCode = "500", description = "Error fetching privilege",
            content = @Content(mediaType = "application/json"))
    @GetMapping(path = PrivilegePriv.GET_PRIVILEGE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getPrivilege(String privilegeId) {
        Privilege privilege = privilegeService.getPrivilege(privilegeId);
        if (privilege == null) {
            logger.info("Could not get privilege for privilege id {}", privilegeId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(privilege);
        }
        logger.info("Get privilege; context={}", privilege);
        return ResponseEntity.ok().body(privilege);
    }

    /** Get a privilege by name. */
    @Secured({SuperPrivilege.SUPER_ADMIN, PrivilegePriv.GET_PRIVILEGE_BY_NAME})
    @Operation(
            summary = "Get privilege by name",
            description = "Returns a privilege by its unique name.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "privilegeName", in = ParameterIn.QUERY, required = true, description = "Privilege name", schema = @Schema(implementation = String.class))
    )
    @ApiResponse(responseCode = "200", description = "Privilege found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Privilege.class)))
    @ApiResponse(responseCode = "500", description = "Error fetching privilege",
            content = @Content(mediaType = "application/json"))
    @GetMapping(path = PrivilegePriv.GET_PRIVILEGE_BY_NAME_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getPrivilegeByName(@RequestParam String privilegeName) {
        Privilege privilege = privilegeService.getPrivilegeName(privilegeName);
        if (privilege == null) {
            logger.info("Could not get privilege for privilege id {}", privilegeName);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(privilege);
        }
        logger.info("Get privilege; context={}", privilege);
        return ResponseEntity.ok().body(privilege);
    }

    /** Search privileges. */
    @Secured({SuperPrivilege.SUPER_ADMIN, PrivilegePriv.GET_SEARCH_PRIVILEGE})
    @Operation(
            summary = "Search privileges",
            description = "Search privileges by keyword.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "keyword", in = ParameterIn.QUERY, required = true, description = "Search term", schema = @Schema(implementation = String.class))
    )
    @ApiResponse(responseCode = "200", description = "Search results",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Privilege.class)))
    @ApiResponse(responseCode = "500", description = "Search failed",
            content = @Content(mediaType = "application/json"))
    @GetMapping(path = PrivilegePriv.GET_SEARCH_PRIVILEGE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<Privilege>> searchPrivilege(@RequestParam String keyword) {
        List<Privilege> privileges = privilegeService.searchPrivilege(keyword);
        if (privileges == null) {
            logger.info("Could not find privilege for keyword {}", keyword);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(privileges);
        }
        logger.info("Search privileges; context={}", privileges);
        return ResponseEntity.ok().body(privileges);
    }

    /** Create privilege. */
    @Secured({SuperPrivilege.SUPER_ADMIN, PrivilegePriv.CREATE_PRIVILEGE})
    @Operation(
            summary = "Create privilege",
            description = "Creates a new privilege.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Privilege.class),
                            examples = @ExampleObject(
                                    value = "{\"name\":\"manage_users\",\"description\":\"Manage Users\",\"path\":\"/users/**\",\"service\":\"auth\"}"
                            )
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Created",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Creation failed",
            content = @Content(mediaType = "text/plain"))
    @PostMapping(path = PrivilegePriv.CREATE_PRIVILEGE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @Override
    public ResponseEntity<?> createPrivilege(@org.springframework.web.bind.annotation.RequestBody Privilege body) {
        if (privilegeService.createPrivilege(body)) {
            logger.info("Privilege created successful; context={}", body);
            return ResponseEntity.ok("INSERTED PRIVILEGE SUCCESSFULLY");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("INSERT PRIVILEGE FAILED");
    }

    /** Update privilege by ID. */
    @Secured({SuperPrivilege.SUPER_ADMIN, PrivilegePriv.EDIT_PRIVILEGE})
    @Operation(
            summary = "Update privilege",
            description = "Updates an existing privilege by ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "privilegeId", in = ParameterIn.PATH, required = true, description = "Privilege ID"),
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Privilege.class))
            )
    )
    @ApiResponse(responseCode = "200", description = "Updated",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Update failed",
            content = @Content(mediaType = "text/plain"))
    @PutMapping(path = PrivilegePriv.EDIT_PRIVILEGE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.TEXT_PLAIN_VALUE)
    @Override
    public ResponseEntity<?> updatePrivilege(@PathVariable String privilegeId,
                                             @org.springframework.web.bind.annotation.RequestBody Privilege body) {
        if (privilegeService.updatePrivilege(privilegeId, body)) {
            logger.info("Privilege updated successful; context={}", body);
            return ResponseEntity.ok("UPDATED PRIVILEGE SUCCESSFULLY");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("UPDATE PRIVILEGE FAILED");
    }

    /** Delete privilege by ID. */
    @Secured({SuperPrivilege.SUPER_ADMIN, PrivilegePriv.DELETE_PRIVILEGE})
    @Operation(
            summary = "Delete privilege",
            description = "Deletes a privilege by ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "privilegeId", in = ParameterIn.PATH, required = true, description = "Privilege ID")
    )
    @ApiResponse(responseCode = "200", description = "Deleted",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Delete failed",
            content = @Content(mediaType = "text/plain"))
    @GetMapping(path = PrivilegePriv.DELETE_PRIVILEGE_PATH, produces = MediaType.TEXT_PLAIN_VALUE)
    @Override
    public ResponseEntity<?> deletePrivilege(String privilegeId) {
        if (privilegeService.deletePrivilege(privilegeId)) {
            logger.info("Delete Privilege successful");
            return ResponseEntity.ok("DELETED Privilege : " + privilegeId);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("DELETE FAILED FOR Privilege: " + privilegeId );
    }
}

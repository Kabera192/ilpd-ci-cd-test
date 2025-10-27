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

import rw.ac.ilpd.mis.auth.service.RoleService;
import rw.ac.ilpd.mis.shared.api.RoleApi;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.privilege.auth.RolePriv;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.dto.role.Role;
import rw.ac.ilpd.mis.shared.util.helpers.MisResponse;
import rw.ac.ilpd.mis.shared.util.helpers.PagedResponse;

import java.util.List;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 24/07/2024
 */

@Tag(
        name = "Roles",
        description = "Role management APIs (list, fetch, search, create, update, delete)"
)
@RestController
@RequestMapping(MisConfig.AUTH_PATH + RolePriv.GET_ROLES_PATH)
public class RoleController implements RoleApi {

    @Autowired
    RoleService roleService;

    private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Secured({SuperPrivilege.SUPER_ADMIN, RolePriv.GET_ROLES})
    @Operation(
            summary = "List roles (paged)",
            description = "Returns a paginated list of roles.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "page",  in = ParameterIn.QUERY, description = "Page index (0-based)", schema = @Schema(implementation = Integer.class), example = "0"),
                    @Parameter(name = "size",  in = ParameterIn.QUERY, description = "Page size", schema = @Schema(implementation = Integer.class), example = "20"),
                    @Parameter(name = "sortBy",in = ParameterIn.QUERY, description = "Sort property (e.g., `id`, `name`)", schema = @Schema(implementation = String.class), example = "id")
            }
    )
    @ApiResponse(responseCode = "200", description = "Roles queried successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "404", description = "No roles found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "500", description = "Server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getAllRoles(@RequestParam(defaultValue = "0") int page,
                                         @RequestParam(defaultValue = "20") int size,
                                         @RequestParam(defaultValue = "id") String sortBy){
        try{
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<Role> rolePage = roleService.getRoles(pageable);
            if (rolePage.isEmpty()) {
                logger.info("No roles found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MisResponse(false, "No roles found"));
            }
            logger.info("Get roleList; size={}", rolePage.getTotalElements());
            PagedResponse response = new PagedResponse(rolePage);
            return ResponseEntity.ok().body(new MisResponse<>(true, "Roles queried successfully.", response));
        }catch (Exception e){
            logger.error("Roles failed to find", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Roles users failed"));
        }
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, RolePriv.GET_UNIT_ROLES})
    @Operation(
            summary = "List roles in a unit",
            description = "Returns roles associated with a specific unit.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "unitId", in = ParameterIn.PATH, required = true, description = "Unit ID", schema = @Schema(implementation = String.class))
    )
    @ApiResponse(responseCode = "200", description = "Unit roles returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class)))
    @ApiResponse(responseCode = "500", description = "Error fetching unit roles",
            content = @Content(mediaType = "application/json"))
    @GetMapping(path = RolePriv.GET_UNIT_ROLES_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<Role>> getUnitRoles(@PathVariable String unitId){
        List<Role> roleList = roleService.getUnitRoles(unitId);
        if (roleList == null) {
            logger.info("Could not get all unit roleList unitId={}", unitId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        logger.info("Get unit roleList; size={}, unit={}", roleList.size(), unitId);
        return ResponseEntity.ok().body(roleList);
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, RolePriv.GET_ROLE})
    @Operation(
            summary = "Get role by ID",
            description = "Returns a role by its identifier.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "roleId", in = ParameterIn.PATH, required = true, description = "Role ID", schema = @Schema(implementation = String.class))
    )
    @ApiResponse(responseCode = "200", description = "Role found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class)))
    @ApiResponse(responseCode = "500", description = "Error fetching role",
            content = @Content(mediaType = "application/json"))
    @GetMapping(path = RolePriv.GET_ROLE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getRole(@PathVariable String roleId) {
        Role role = roleService.getRole(roleId);
        if (role == null) {
            logger.info("Could not get role for role id {}", roleId);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(role);
        }
        logger.info("Get role; context={}", role);
        return ResponseEntity.ok().body(role);
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, RolePriv.GET_ROLE_BY_NAME})
    @Operation(
            summary = "Get role by name",
            description = "Returns a role by its unique name.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "roleName", in = ParameterIn.QUERY, required = true, description = "Role name", schema = @Schema(implementation = String.class))
    )
    @ApiResponse(responseCode = "200", description = "Role found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class)))
    @ApiResponse(responseCode = "500", description = "Error fetching role",
            content = @Content(mediaType = "application/json"))
    @GetMapping(path = RolePriv.GET_ROLE_BY_NAME_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getRoleByName(@RequestParam String roleName) {
        Role role = roleService.getRoleName(roleName);
        if (role == null) {
            logger.info("Could not get role for role id {}", roleName);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(role);
        }
        logger.info("Get role; context={}", role);
        return ResponseEntity.ok().body(role);
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, RolePriv.GET_SEARCH_ROLE})
    @Operation(
            summary = "Search roles",
            description = "Search roles by keyword.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "keyword", in = ParameterIn.QUERY, required = true, description = "Search term", schema = @Schema(implementation = String.class))
    )
    @ApiResponse(responseCode = "200", description = "Search results",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = Role.class)))
    @ApiResponse(responseCode = "500", description = "Search failed",
            content = @Content(mediaType = "application/json"))
    @GetMapping(path = RolePriv.GET_SEARCH_ROLE_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<List<Role>> searchRole(@RequestParam String keyword) {
        List<Role> roles = roleService.searchRole(keyword);
        if (roles == null) {
            logger.info("Could not find role for keyword {}", keyword);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(roles);
        }
        logger.info("Search roles; context={}", roles);
        return ResponseEntity.ok().body(roles);
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, RolePriv.CREATE_ROLE})
    @Operation(
            summary = "Create role",
            description = "Creates a new role.",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Role.class)
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Created",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Creation failed",
            content = @Content(mediaType = "text/plain"))
    @PostMapping(path = RolePriv.CREATE_ROLE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> createRole(@org.springframework.web.bind.annotation.RequestBody Role body) {
        try{
            if(body.getPrivileges()== null || body.getPrivileges().size() < 1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MisResponse<>(false,"INSERT ROLE FAILED, at least one privilege is required"));
            logger.info("Create role; context={}", body);
            Role role = roleService.createRole(body);
            if ( role != null) {
                logger.info("Role created successful; context={}", body);
                return ResponseEntity.status (HttpStatus.OK).body( new MisResponse<>(true, "INSERTED ROLE SUCCESSFULLY", role));
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new MisResponse<>(false , "INSERT ROLE FAILED"));
        }catch (Exception e){
            logger.info("Could not create role", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Create role failed: " + e.getMessage()));
        }
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, RolePriv.EDIT_ROLE})
    @Operation(
            summary = "Update role",
            description = "Updates an existing role by ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "roleId", in = ParameterIn.PATH, required = true, description = "Role ID"),
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Role.class))
            )
    )
    @ApiResponse(responseCode = "200", description = "Updated",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Update failed",
            content = @Content(mediaType = "text/plain"))
    @PutMapping(path = RolePriv.EDIT_ROLE_PATH, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> updateRole(@PathVariable String roleId,
                                        @org.springframework.web.bind.annotation.RequestBody Role body) {

        if(body.getPrivileges()== null || body.getPrivileges().size() < 1) return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Update ROLE FAILED, at least one privilege is required");
        Role role = roleService.updateRole(roleId, body);
        if (role != null) {
            logger.info("Role updated successful; context={}", body);
            return ResponseEntity.status (HttpStatus.OK).body( new MisResponse<>(true, "UPDATED ROLE SUCCESSFULLY",  role));
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("UPDATE ROLE FAILED");
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, RolePriv.DELETE_ROLE})
    @Operation(
            summary = "Delete role",
            description = "Deletes a role by ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "roleId", in = ParameterIn.PATH, required = true, description = "Role ID")
    )
    @ApiResponse(responseCode = "200", description = "Deleted",
            content = @Content(mediaType = "text/plain"))
    @ApiResponse(responseCode = "500", description = "Delete failed",
            content = @Content(mediaType = "text/plain"))
    @GetMapping(path = RolePriv.DELETE_ROLE_PATH, produces = MediaType.TEXT_PLAIN_VALUE)
    @Override
    public ResponseEntity<?> deleteRole(String roleId) {
        if (roleService.deleteRole(roleId)) {
            logger.info("Delete Role successful");
            return ResponseEntity.ok("DELETED Role : " + roleId);
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("DELETE FAILED FOR Role: " + roleId );
    }
}

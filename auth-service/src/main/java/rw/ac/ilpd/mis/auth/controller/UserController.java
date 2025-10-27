package rw.ac.ilpd.mis.auth.controller;

import com.warrenstrange.googleauth.GoogleAuthenticatorKey;
import jakarta.validation.Valid;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import rw.ac.ilpd.mis.auth.service.UserService;
import rw.ac.ilpd.mis.auth.util.AuthUtil;
import rw.ac.ilpd.mis.shared.api.UserApi;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.privilege.auth.SuperPrivilege;
import rw.ac.ilpd.mis.shared.config.privilege.auth.UserPriv;
import rw.ac.ilpd.mis.shared.dto.auth.request.ChangePasswordRequest;
import rw.ac.ilpd.mis.shared.dto.auth.request.CreateUserRequest;
import rw.ac.ilpd.mis.shared.dto.auth.request.UpdateUserRequest;
import rw.ac.ilpd.mis.shared.dto.auth.response.UserProfileResponse;
import rw.ac.ilpd.mis.shared.dto.user.User;
import rw.ac.ilpd.mis.shared.util.helpers.MisResponse;
import rw.ac.ilpd.mis.shared.util.helpers.MapBuilder;
import rw.ac.ilpd.mis.shared.util.helpers.PagedResponse;
import rw.ac.ilpd.mis.shared.security.Secured;

import java.util.List;
import java.util.UUID;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 24/07/2024
 */

@Tag(
        name = "Users",
        description = "User management, profile, and 2FA operations in the Authentication Service"
)
@RestController
@RequestMapping(MisConfig.AUTH_PATH + UserPriv.GET_USERS_PATH)
public class UserController implements UserApi {

    @Autowired UserService userService;
    @Autowired AuthUtil authUtil;

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Secured({"SuperAdmin", UserPriv.GET_USERS})
    @Operation(
            summary = "List users (paged)",
            description = "Returns a paginated list of users.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = {
                    @Parameter(name = "page", description = "Page index (0-based)", in = ParameterIn.QUERY, schema = @Schema(implementation = Integer.class)),
                    @Parameter(name = "size", description = "Page size", in = ParameterIn.QUERY, schema = @Schema(implementation = Integer.class)),
                    @Parameter(name = "sortBy", description = "Sort property (e.g., `id`, `email`)", in = ParameterIn.QUERY, schema = @Schema(implementation = String.class))
            }
    )
    @ApiResponse(responseCode = "200", description = "Users queried successfully",
            content = @Content(mediaType = "application/json",
                    schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "404", description = "No users found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "500", description = "Server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getAllUsers(@RequestParam(name = "page", defaultValue = "0") int page,
                                         @RequestParam(name = "size", defaultValue = "20") int size,
                                         @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        try{
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<User> userPage = userService.getAllUsers(pageable);
            if (userPage.isEmpty()) {
                logger.info("No users found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MisResponse(false, "No users found"));
            }
            logger.info("Get userList; size={}", userPage.getTotalElements());
            PagedResponse response = new PagedResponse(userPage);
            return ResponseEntity.ok().body(new MisResponse<>(true, "Users queried successfully.", response));
        }catch (Exception e){
            logger.error("Users failed to find", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Retrieve users failed"));
        }
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, UserPriv.GET_UNIT_USERS})
    @GetMapping(path = UserPriv.GET_UNIT_USERS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getUnitUsers(@PathVariable String unitId, @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "20") int size,
                                          @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        try{
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<User> users = userService.getUnitUsers(unitId, pageable);
            if (users.isEmpty()) {
                logger.info("No unit users found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MisResponse(false, "No unit users found"));
            }
            logger.info("Get unit userList; size={}", users.getTotalElements());
            PagedResponse response = new PagedResponse(users);
            return ResponseEntity.ok().body(new MisResponse<>(true, "Unit users queried successfully.", response));
        }catch (Exception e){
            logger.error("Unit users failed to find", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Retrieve unit users failed"));
        }
    }


    @Secured({SuperPrivilege.SUPER_ADMIN, UserPriv.GET_ROLE_USERS})
    @GetMapping(path = UserPriv.GET_ROLE_USERS_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getRoleUsers(@PathVariable String roleId, @RequestParam(name = "page", defaultValue = "0") int page,
                                          @RequestParam(name = "size", defaultValue = "20") int size,
                                          @RequestParam(name = "sortBy", defaultValue = "id") String sortBy) {
        try{
            Pageable pageable = PageRequest.of(page, size, Sort.by(sortBy));
            Page<User> users = userService.getRoleUsers(roleId, pageable);
            if (users.isEmpty()) {
                logger.info("No unit users found");
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MisResponse(false, "No unit users found"));
            }
            logger.info("Get unit userList; size={}", users.getTotalElements());
            PagedResponse response = new PagedResponse(users);
            return ResponseEntity.ok().body(new MisResponse<>(true, "Unit users queried successfully.", response));
        }catch (Exception e){
            logger.error("Unit users failed to find", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Retrieve unit users failed"));
        }
    }

    @Secured({UserPriv.GET_USER_BY_ID, SuperPrivilege.SUPER_ADMIN})
    @GetMapping( path=UserPriv.GET_USER_BY_ID_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        try{
            User user = userService.getUserById(userId);
            if (user == null) {
                return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MisResponse(false, "User not found"));
            }
            UserProfileResponse response = userService.convertToUserProfile(user);
            return ResponseEntity.ok().body(new MisResponse<>(true, "User queried successfully.", response));

        }catch (Exception e){
            logger.error("User failed to find with id : " + userId, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Retrieve user failed with id " + userId));
        }
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, UserPriv.GET_USER_BY_USERNAME})
    @GetMapping( path=UserPriv.GET_USER_BY_USERNAME_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getByUsername(@PathVariable String username) {
        try{
            User user = userService.findByUsername(username);
            if (user == null) {
                return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MisResponse(false, "User not found"));
            }
            UserProfileResponse response = userService.convertToUserProfile(user);
            return ResponseEntity.ok().body(new MisResponse<>(true, "User queried successfully.", response));

        }catch (Exception e){
            logger.error("User failed to find with username : " + username, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Retrieve user failed with username: " + username));
        }
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, UserPriv.GET_USER_BY_EMAIL})
    @GetMapping( path=UserPriv.GET_USER_BY_EMAIL_PATH, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> getByEmail(@PathVariable String email) {

        try{
            User user = userService.findByEmail(email);
            if (user == null) {
                return  ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MisResponse(false, "User not found"));
            }
            UserProfileResponse response = userService.convertToUserProfile(user);
            return ResponseEntity.ok().body(new MisResponse<>(true, "User queried successfully.", response));

        }catch (Exception e){
            logger.error("User failed to find with email : " + email, e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Retrieve user failed with email: " + email));
        }
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, UserPriv.CREATE_USER})
    @Operation(
            summary = "Create user",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreateUserRequest.class))
            )
    )
    @ApiResponse(responseCode = "200", description = "User created",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "409", description = "User already exists",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "400", description = "Validation error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @PostMapping(path = UserPriv.CREATE_USER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> createUser(@Valid @RequestBody CreateUserRequest createUserRequest) {
        try{
            if (userService.findByEmail(createUserRequest.email) != null
                    || userService.findByUsername(createUserRequest.username) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new MisResponse(false, "User with same email or username already exists"));
            }
            User user = userService.registerUser(createUserRequest.toUser());
            return ResponseEntity.ok().body(new MisResponse<>(true, "User created successfully", user));
        }catch (Exception e){
            logger.error("Error creating user", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MisResponse<>(false, "User creation failed: " + e.getClass().getSimpleName()));
        }
    }

    @Secured({SuperPrivilege.SUPER_ADMIN, UserPriv.UPDATE_USER})
    @Operation(
            summary = "Update user",
            description = "Updates an existing user by ID.",
            security = @SecurityRequirement(name = "bearerAuth"),
            parameters = @Parameter(name = "userId", description = "User ID (UUID)", in = ParameterIn.PATH)
    )
    @ApiResponse(responseCode = "200", description = "User updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "409", description = "User not found / email or username not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "500", description = "Server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @PutMapping(path = UserPriv.UPDATE_USER_PATH, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> updateUser(@PathVariable String userId,
                                        @Valid @RequestBody UpdateUserRequest updateUserRequest) {
        try{
            if (userService.findByEmail(updateUserRequest.email) == null
                    && userService.findByUsername(updateUserRequest.username) == null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new MisResponse(false, "User with  email and username does not exist"));
            }
            User user = userService.updateUser(userId, updateUserRequest.toUser());
            return ResponseEntity.ok().body(new MisResponse<>(true, "User updated successfully", user));
        }catch (Exception e){
            logger.error("Error updating user", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "User update failed"));
        }
    }

    @Operation(
            summary = "Enable 2FA via Authenticator app",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "2FA enabled (AuthApp)",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @PostMapping("/enable-2fa-authapp")
    @Override
    public ResponseEntity<?> enable2FAApp(){
        try{
            UUID userId = authUtil.loggedInUserId();
            if(userId == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MisResponse(false, "User not found"));
            }
            GoogleAuthenticatorKey secret = userService.generate2FASecret(String.valueOf(userId));
            User user = userService.getUserById(String.valueOf(userId));
            String qrCodeUrl = userService.getGoogleQrCodeUrl(secret, user.getEmail());
            userService.enable2FA(String.valueOf(userId), "AUTHAPP");
            return ResponseEntity.ok(new MisResponse<>(true, "AUTHAPP 2FA enabled",
                    MapBuilder.buildMap("qrCodeUrl", qrCodeUrl, "email", user.getEmail())));
        }catch (Exception e){
            logger.error("Error enabling Authenticator 2FA", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Enabling Authenticator 2FA failed"));
        }
    }

    @Operation(
            summary = "Enable 2FA via Email",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "2FA enabled (Email)",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @PostMapping("/enable-2fa-email")
    @Override
    public ResponseEntity<?> enable2FAEmail(){
        try{
            UUID userId = authUtil.loggedInUserId();
            if(userId == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MisResponse(false, "User not found"));
            }
            GoogleAuthenticatorKey secret = userService.generate2FASecret(String.valueOf(userId));
            User user = userService.getUserById(String.valueOf(userId));
            String qrCodeUrl = userService.getGoogleQrCodeUrl(secret, user.getEmail());
            userService.enable2FA(String.valueOf(userId), "EMAIL");
            return ResponseEntity.ok(new MisResponse<>(true, "Email 2FA enabled",
                    MapBuilder.buildMap("qrCodeUrl", qrCodeUrl, "email", user.getEmail())));
        }catch (Exception e){
            logger.error("Error enabling Email 2FA", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Enabling Email 2FA failed"));
        }
    }

    @Operation(
            summary = "Get 2FA status",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "2FA status",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @GetMapping("/2fa-status")
    @Override
    public ResponseEntity<?> get2FAStatus(){
        try{
            User user = authUtil.loggedInUser();
            if (user != null){
                return ResponseEntity.ok().body(new MisResponse<>(true, "2FA Status",
                        MapBuilder.buildMap("is2faEnabled", user.isTwoFactorEnabled(), "2faMethod", user.getSignUpMethod())));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MisResponse<>(false, "2FA Status"));
            }
        }catch (Exception e){
            logger.error("Error getting 2FA status", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Error getting 2FA status"));
        }
    }

    @Operation(
            summary = "Disable 2FA",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "2FA disabled",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @PostMapping("/disable-2fa")
    @Override
    public ResponseEntity<?> disable2FA(){
        try{
            UUID userId = authUtil.loggedInUserId();
            if(userId == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MisResponse(false, "User not found"));
            }
            userService.disable2FA(String.valueOf(userId));
            return ResponseEntity.ok(new MisResponse<>(true, "2FA disabled",
                    MapBuilder.buildMap("userId", userId)));
        }catch (Exception e){
            logger.error("Error disabling 2FA", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Error disabling 2FA"));
        }
    }

    @Operation(
            summary = "Update password",
            security = @SecurityRequirement(name = "bearerAuth"),
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    required = true,
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ChangePasswordRequest.class))
            )
    )
    @ApiResponse(responseCode = "200", description = "Password updated",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "404", description = "Invalid credentials / user not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "500", description = "Server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @PostMapping("/update-password")
    @Override
    public ResponseEntity<?> updatePassword(@RequestBody ChangePasswordRequest changePasswordRequest ){
        try {
            User user = authUtil.loggedInUser();
            boolean validCredentials = userService.verifyPassword(changePasswordRequest.getUsername(), changePasswordRequest.getOldPassword()) ;
            if (user != null && validCredentials){
                userService.updatePassword(String.valueOf(user.getId()), changePasswordRequest.getNewPassword());
                return ResponseEntity.ok().body(new MisResponse<>(true, "Password updated successfully",
                        MapBuilder.buildMap("is2faEnabled", user.isTwoFactorEnabled(), "2faMethod", user.getSignUpMethod(),
                                "username", user.getUsername(), "updated", user.getUpdatedAt())));
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body(new MisResponse<>(false, "Invalid credentials, User not found"));
            }
        } catch (RuntimeException e) {
            logger.error("Error updating password", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Password update failed"));
        }
    }

    @Operation(
            summary = "Get current user details from token",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "User found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @GetMapping("/token/user")
    @Override
    public ResponseEntity<?> getUserDetails( @AuthenticationPrincipal UserDetails userDetails){
        try{
            User user = userService.findByUsername(userDetails.getUsername());
            if(user == null){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MisResponse<>(true, "User not found."));
            }
            UserProfileResponse response = userService.convertToUserProfile(user);
            return ResponseEntity.ok().body(new MisResponse<>(true, "User is found.", response));
        }catch (Exception e){
            logger.error("Error getting user details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Finding user details failed"));
        }
    }

    @Operation(
            summary = "Get current username",
            security = @SecurityRequirement(name = "bearerAuth")
    )
    @ApiResponse(responseCode = "200", description = "Username returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "404", description = "User not found",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @GetMapping("/username")
    @Override
    public ResponseEntity<?> currentUsername(
            @Parameter(hidden = true) @AuthenticationPrincipal UserDetails userDetails){
        try{
            return (userDetails != null)
                    ? ResponseEntity.ok(new MisResponse<>(true, "User is found.",
                    MapBuilder.buildMap("username", userDetails.getUsername())))
                    : ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MisResponse<>(false, "User is not found", HttpStatus.NOT_FOUND));
        }catch (Exception e){
            logger.error("Error getting user details", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Finding user details failed"));
        }
    }
}

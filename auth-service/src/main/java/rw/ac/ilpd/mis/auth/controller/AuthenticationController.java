package rw.ac.ilpd.mis.auth.controller;

import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.parameters.RequestBody;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import rw.ac.ilpd.mis.auth.service.TotpService;
import rw.ac.ilpd.mis.auth.service.impl.UserDetailsImpl;
import rw.ac.ilpd.mis.auth.service.impl.mapper.UserMapper;
import rw.ac.ilpd.mis.shared.api.AuthApi;
import rw.ac.ilpd.mis.shared.config.MisConfig;
import rw.ac.ilpd.mis.shared.config.privilege.auth.PublicPriv;
import rw.ac.ilpd.mis.shared.dto.auth.request.*;
import rw.ac.ilpd.mis.shared.dto.auth.response.AuthenticationResponse;
import rw.ac.ilpd.mis.shared.dto.auth.response.TotpResponse;
import rw.ac.ilpd.mis.shared.dto.user.User;
import rw.ac.ilpd.mis.shared.security.Secured;
import rw.ac.ilpd.mis.shared.util.helpers.MisResponse;
import rw.ac.ilpd.mis.auth.service.AuthenticationService;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author UWANTWALI Zigama Didier, zigdidier@gmail.com, +250 788660270
 * @project mis
 * @date 24/07/2024
 */

@Tag(name = "Authentication", description = "Public authentication endpoints: sign in/out, password reset, and 2FA verification")
@RestController
@RequestMapping(MisConfig.AUTH_PATH)
public class AuthenticationController implements AuthApi {

    @Autowired AuthenticationService authenticationService;
    @Autowired TotpService totpService;
    @Autowired private UserMapper userMapper;

    private static final Logger logger = LoggerFactory.getLogger(AuthenticationController.class);

    @Operation(
            summary = "Sign in",
            description = "Authenticates a user. If 2FA is enabled, returns a TOTP challenge payload; otherwise returns a JWT.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = AuthenticationRequest.class)
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Authenticated; JWT returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "202", description = "2FA required; TOTP challenge issued",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "500", description = "Authentication failed",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @PostMapping(path="/public/signin", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> login(@org.springframework.web.bind.annotation.RequestBody AuthenticationRequest loginRequest) {
        try{
            UserDetailsImpl userDetails = authenticationService.authenticate(loginRequest.getEmail(), loginRequest.getPassword());

            if (userDetails.is2faEnabled()) {
                String loginCode = totpService.createUniqueId();
                totpService.generateOtp(userDetails.getId().toString(), loginCode);
                return ResponseEntity.status(HttpStatus.ACCEPTED)
                        .body(new MisResponse<>(true, "2FA enabled. Use OTP sent to your registered device.",
                                new TotpResponse(
                                        userDetails.getId().toString(),
                                        userDetails.getUsername(),
                                        userDetails.getEmail(),
                                        userDetails.is2faEnabled(),
                                        userDetails.getSignUpMethod(),
                                        loginCode
                                )));
            } else {
                String jwtToken = authenticationService.generateToken(userDetails);
                List<String> privileges = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());
                AuthenticationResponse response =
                        new AuthenticationResponse(userDetails.getUsername(), privileges, jwtToken);
                logger.info("Login successful and JWT generated for user {}", userDetails.getUsername());
                return ResponseEntity.ok(new MisResponse<>(true, "User is authenticated successfully!", response));
            }
        } catch (Exception e) {
            logger.error("Login failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "User authentication failed"));
        }
    }

    @Operation(
            summary = "Logout",
            description = "Invalidates the provided JWT (server-side logout / blacklist).",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = LogoutRequest.class))
            )
    )
    @ApiResponse(responseCode = "200", description = "Logged out",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "500", description = "Logout failed",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @PostMapping(path ="/public/logout", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> logout(@org.springframework.web.bind.annotation.RequestBody LogoutRequest logoutRequest) {
        try{
            authenticationService.logout(logoutRequest.getToken());
            return ResponseEntity.ok(new MisResponse<>(true, "User logged out successfully."));
        } catch (Exception e) {
            logger.error("Logout failed", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Logout failed"));
        }
    }

    @Operation(
            summary = "Request password reset email",
            description = "Sends a password reset link to the user’s email.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ForgotPasswordRequest.class)
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Password reset email sent",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "500", description = "Error sending email",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @PostMapping(path="/public/forgot-password", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> forgotPassword(@org.springframework.web.bind.annotation.RequestBody ForgotPasswordRequest forgotPasswordRequest) {
        try {
            authenticationService.generatePasswordResetToken(forgotPasswordRequest.getEmail());
            return ResponseEntity.ok(new MisResponse<>(true, "Password reset email sent!", forgotPasswordRequest ));
        } catch (Exception e) {
            logger.error("Forgot password reset email failed: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "Error sending password reset email"));
        }
    }

    @Operation(
            summary = "Reset password",
            description = "Resets the user’s password using the reset token.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ResetPasswordRequest.class))
            )
    )
    @ApiResponse(responseCode = "200", description = "Password reset successful",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "400", description = "Invalid token or request",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @PostMapping(path="/public/reset-password", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> resetPassword(@org.springframework.web.bind.annotation.RequestBody ResetPasswordRequest resetPasswordRequest) {
        try {
            authenticationService.resetPassword(resetPasswordRequest.getToken(), resetPasswordRequest.getNewPassword());
            return ResponseEntity.ok(new MisResponse<>(true, "Password reset successful"));
        } catch (Exception e) {
            logger.error("Password reset failed: ", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MisResponse<>(false, "Password reset failed"));
        }
    }

    @Operation(
            summary = "Verify 2FA login (TOTP)",
            description = "Verifies a TOTP code and returns a JWT on success.",
            requestBody = @RequestBody(
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = TotpRequest.class)
                    )
            )
    )
    @ApiResponse(responseCode = "200", description = "Authenticated; JWT returned",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "401", description = "OTP verification failed",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @ApiResponse(responseCode = "500", description = "Server error",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = MisResponse.class)))
    @PostMapping(path="/public/verify-2fa-login", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> verify2FALogin(@Valid @org.springframework.web.bind.annotation.RequestBody TotpRequest totpRequest){
        try{
            User user = authenticationService.validate2FACode(totpRequest);
            if (user != null) {
                UserDetailsImpl userDetails = UserDetailsImpl.build(authenticationService.getUserEntityById(user.getId()));
                String token = authenticationService.generateToken(userDetails);
                List<String> roles = userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList());
                AuthenticationResponse response =
                        new AuthenticationResponse(userDetails.getUsername(), roles, token);
                //logger.info("Auth Response : {}", response);
                return ResponseEntity.ok(new MisResponse<>(true, "User is authenticated successfully!", response));
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                        .body(new MisResponse<>(false, "OTP verification has failed!"));
            }
        } catch (Exception e) {
            logger.error("OTP verification failed: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MisResponse<>(false, "OTP verification failed"));
        }
    }


    @Operation(
            summary = "Create a public user",
            description = "Registers a new public user if email and username are unique."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User created",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MisResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email or username already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MisResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MisResponse.class)))
    })

    @PostMapping(path = "/public/account-create", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> createPublicUser(
            @Valid
            @RequestBody(
                    required = true,
                    description = "User registration payload",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreatePublicUserRequest.class)
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody CreatePublicUserRequest createPublicUserRequest
    ) {
        try {
            if (authenticationService.findByEmail(createPublicUserRequest.email) != null
                    || authenticationService.findByUsername(createPublicUserRequest.username) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new MisResponse(false, "User with same email or username already exists"));
            }
            User user = authenticationService.registerPublicUser(createPublicUserRequest.toUser());
            return ResponseEntity.ok(new MisResponse<>(true, "User created successfully", user));
        } catch (Exception e) {
            logger.error("Error creating user", e); // log the throwable
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MisResponse<>(false, "User creation failed: " + e.getClass().getSimpleName()));
        }
    }


    @Operation(
            summary = "Update a public user",
            description = "Updates an existing public user. Email/username must remain unique."
    )
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "User updated",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MisResponse.class))),
            @ApiResponse(responseCode = "409", description = "Email or username already exists",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MisResponse.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request payload",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = MisResponse.class)))
    })
    @Secured(PublicPriv.PUBLIC)
    @PutMapping(path = "/public/account-update", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @Override
    public ResponseEntity<?> updatePublicUser(
            @Valid
            @RequestBody(
                    required = true,
                    description = "User update payload",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = CreatePublicUserRequest.class)
                    )
            )
            @org.springframework.web.bind.annotation.RequestBody CreatePublicUserRequest createPublicUserRequest
    ) {
        try {
            if (authenticationService.findByEmail(createPublicUserRequest.email) != null
                    || authenticationService.findByUsername(createPublicUserRequest.username) != null) {
                return ResponseEntity.status(HttpStatus.CONFLICT)
                        .body(new MisResponse(false, "User with same email or username already exists"));
            }
            // TODO: call your update method; currently this calls registerPublicUser
            User user = authenticationService.registerPublicUser(createPublicUserRequest.toUser());
            return ResponseEntity.ok(new MisResponse<>(true, "User updated successfully", user));
        } catch (Exception e) {
            logger.error("Error updating user", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MisResponse<>(false, "User update failed: " + e.getClass().getSimpleName()));
        }
    }


}

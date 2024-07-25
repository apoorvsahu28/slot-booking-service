package com.nagarro.SlotBooking.controller;

import com.nagarro.SlotBooking.dto.*;
import com.nagarro.SlotBooking.model.User;
import com.nagarro.SlotBooking.service.AuthenticationService;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Authentication API", description = "API for user authentication and registration")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Authentication successful",
                    content = @Content(
                            schema = @Schema(implementation = AuthenticationResponse.class))),
            @ApiResponse(
                    responseCode = "401",
                    description = "Unauthorized",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class))),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)))
    })
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody LoginRequestDto request
            ) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.status(HttpStatus.OK).body(response);

    }

    @PostMapping("/register")
    public ResponseEntity<UserResponseDto> register(@RequestBody RegistrationRequestDto request) {
        UserResponseDto user = authenticationService.register(request);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}

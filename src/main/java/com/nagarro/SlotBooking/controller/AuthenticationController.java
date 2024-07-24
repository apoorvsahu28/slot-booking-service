package com.nagarro.SlotBooking.controller;

import com.nagarro.SlotBooking.dto.AuthenticationResponse;
import com.nagarro.SlotBooking.model.User;
import com.nagarro.SlotBooking.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> authenticate(
            @RequestBody User request
            ) {
        AuthenticationResponse response = authenticationService.authenticate(request);
        return ResponseEntity.ok(response);

    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User request) {
        User user = authenticationService.register(request);
        return ResponseEntity.ok(user);
    }
}

package com.nagarro.SlotBooking.service.impl;

import com.nagarro.SlotBooking.dto.AuthenticationResponse;
import com.nagarro.SlotBooking.dto.LoginRequestDto;
import com.nagarro.SlotBooking.dto.RegistrationRequestDto;
import com.nagarro.SlotBooking.dto.UserResponseDto;
import com.nagarro.SlotBooking.exception.AuthenticationException;
import com.nagarro.SlotBooking.model.User;
import com.nagarro.SlotBooking.repository.UserRepository;
import com.nagarro.SlotBooking.security.JwtService;
import com.nagarro.SlotBooking.service.AuthenticationService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {

    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationServiceImpl(JwtService jwtService, AuthenticationManager authenticationManager, UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public AuthenticationResponse authenticate(LoginRequestDto request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUsername(),
                            request.getPassword()
                    )
            );
        } catch (Exception e) {
            throw new AuthenticationException("Authentication failed: " + e.getMessage());
        }

        User user = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AuthenticationException("User not found"));

        return new AuthenticationResponse(jwtService.generateToken(user));
    }

    @Override
    public UserResponseDto register(RegistrationRequestDto request) {
        User user = new User();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        user.setRoles(request.getRoles());
        User savedUser = userRepository.save(user);
        return new UserResponseDto(savedUser.getId(), savedUser.getUsername(), savedUser.getRoles());
    }
}

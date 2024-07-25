package com.nagarro.SlotBooking.service;

import com.nagarro.SlotBooking.dto.AuthenticationResponse;
import com.nagarro.SlotBooking.dto.LoginRequestDto;
import com.nagarro.SlotBooking.dto.RegistrationRequestDto;
import com.nagarro.SlotBooking.dto.UserResponseDto;
import com.nagarro.SlotBooking.model.User;

public interface AuthenticationService {

    AuthenticationResponse authenticate(LoginRequestDto request);
    UserResponseDto register(RegistrationRequestDto request);
}

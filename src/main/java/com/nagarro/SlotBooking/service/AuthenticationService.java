package com.nagarro.SlotBooking.service;

import com.nagarro.SlotBooking.dto.AuthenticationResponse;
import com.nagarro.SlotBooking.model.User;

public interface AuthenticationService {

    AuthenticationResponse authenticate(User request);
    User register(User request);
}

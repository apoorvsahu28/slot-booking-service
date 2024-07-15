package com.nagarro.SlotBooking.service;

import com.nagarro.SlotBooking.dto.UserRequestDto;

import java.util.Map;

public interface ApiService {

    Map<String, Object> forwardRequest(UserRequestDto userRequest, Map<String, String> headers);
}

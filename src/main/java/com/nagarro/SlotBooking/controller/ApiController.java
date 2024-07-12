package com.nagarro.SlotBooking.controller;

import com.nagarro.SlotBooking.dto.UserRequestDto;
import com.nagarro.SlotBooking.exception.ApiException;
import com.nagarro.SlotBooking.service.ApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }

    @PostMapping("/forward")
    public ResponseEntity<Map<String, Object>> forwardRequest(@RequestBody UserRequestDto userRequest) {
        Map<String, Object> response = apiService.forwardRequest(userRequest);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }
}

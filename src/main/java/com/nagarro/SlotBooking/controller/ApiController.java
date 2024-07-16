package com.nagarro.SlotBooking.controller;

import com.nagarro.SlotBooking.dto.ErrorResponseDto;
import com.nagarro.SlotBooking.dto.UserRequestDto;
import com.nagarro.SlotBooking.service.ApiService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(
        name = "Job Slot Optimization API",
        description = "API for forwarding requests to a target API with dynamic location and generated slots"
)
@RestController
@RequestMapping(path = "/api", produces = MediaType.APPLICATION_JSON_VALUE)
public class ApiController {

    private final ApiService apiService;

    public ApiController(ApiService apiService) {
        this.apiService = apiService;
    }


    @Operation(
            summary = "Forward Request",
            description = "Forwards a request to the target API with user-provided location and dynamically generated slots for the next 4 days.",
            parameters = {
                    @Parameter(name = "authorization", in = ParameterIn.HEADER, description = "Authorization token", required = true, schema = @Schema(type = "string")),
                    @Parameter(name = "x-client-id", in = ParameterIn.HEADER, description = "Client ID", required = true, schema = @Schema(type = "string")),
                    @Parameter(name = "x-client-version", in = ParameterIn.HEADER, description = "Client version", required = true, schema = @Schema(type = "string")),
                    @Parameter(name = "x-account-id", in = ParameterIn.HEADER, description = "Account ID", required = true, schema = @Schema(type = "string")),
                    @Parameter(name = "x-account-name", in = ParameterIn.HEADER, description = "Account name", required = true, schema = @Schema(type = "string")),
                    @Parameter(name = "x-company-id", in = ParameterIn.HEADER, description = "Company ID", required = true, schema = @Schema(type = "string")),
                    @Parameter(name = "x-company-name", in = ParameterIn.HEADER, description = "Company name", required = true, schema = @Schema(type = "string"))
            }
    )
    @ApiResponses({
            @ApiResponse(
                    responseCode = "200",
                    description = "Request forwarded successfully"
            ),
            @ApiResponse(
                    responseCode = "500",
                    description = "Internal server error",
                    content = @Content(
                            schema = @Schema(implementation = ErrorResponseDto.class)
                    )
            )
    }
    )
    @PostMapping("/forward")
    public ResponseEntity<Map<String, Object>> forwardRequest(
            @RequestHeader Map<String, String> headers,
            @RequestBody UserRequestDto userRequest) {

            Map<String, Object> response = apiService.forwardRequest(userRequest, headers);
            return new ResponseEntity<>(response, HttpStatus.OK);
    }
}

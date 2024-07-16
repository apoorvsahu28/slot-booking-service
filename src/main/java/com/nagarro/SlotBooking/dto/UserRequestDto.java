package com.nagarro.SlotBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Schema(
        name = "UserRequest",
        description = "User request data transfer object")
@Data
public class UserRequestDto {

    @Schema(description = "Location object containing latitude and longitude")
    private LocationDto location;
}

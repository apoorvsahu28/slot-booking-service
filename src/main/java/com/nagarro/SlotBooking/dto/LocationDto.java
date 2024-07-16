package com.nagarro.SlotBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(
        name = "Location",
        description = "Location object containing latitude and longitude")
@Data
public class LocationDto {

    @Schema(description = "Latitude of the location", example = "52.5158595")
    private double latitude;

    @Schema(description = "Longitude of the location", example = "13.3175292")
    private double longitude;
}

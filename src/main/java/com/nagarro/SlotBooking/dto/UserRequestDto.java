package com.nagarro.SlotBooking.dto;

import lombok.Data;

import java.util.List;

@Data
public class UserRequestDto {

    private LocationDto location;
    private List<SlotDto> slots;
}

package com.nagarro.SlotBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(
        name = "LoginRequest",
        description = "Schema for user login request containing username and password"
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LoginRequestDto {
    @Schema(
            description = "Username of the user attempting to log in",
            example = "john_doe"
    )
    private String username;

    @Schema(
            description = "Password of the user attempting to log in",
            example = "password123"
    )
    private String password;
}

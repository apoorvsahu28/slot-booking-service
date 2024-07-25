package com.nagarro.SlotBooking.dto;

import com.nagarro.SlotBooking.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(
        name = "RegistrationRequest",
        description = "Schema for user registration request containing username, password, and roles"
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationRequestDto {

    @Schema(
            description = "Username of the user registering",
            example = "john_doe"
    )
    private String username;

    @Schema(
            description = "Password of the user registering",
            example = "password123"
    )
    private String password;

    @Schema(
            description = "List of roles to be assigned to the user",
            example = "[\"ROLE_USER\"]"
    )
    private List<Role> roles;
}

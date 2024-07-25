package com.nagarro.SlotBooking.dto;

import com.nagarro.SlotBooking.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Schema(
        name = "UserResponse",
        description = "Schema for user response after registration containing user ID, username, and roles"
)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {

    @Schema(
            description = "Unique identifier of the user",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "Username of the registered user",
            example = "john_doe"
    )
    private String username;

    @Schema(
            description = "List of roles assigned to the user",
            example = "[\"ROLE_USER\"]"
    )
    private List<Role> roles;
}

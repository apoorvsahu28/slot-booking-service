package com.nagarro.SlotBooking.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Schema(
        name = "AuthenticationResponse",
        description = "Schema to hold authentication response information"
)
@Getter
@Setter
@AllArgsConstructor
public class AuthenticationResponse {

    @Schema(
            description = "Access token provided after successful authentication",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
    )
    private String access_token;

}

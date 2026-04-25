package com.gpsolutions.hotels.domain.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record ContactDto(

    @NotBlank(
        message = "Phone number must be present"
    )
    @Pattern(
        regexp = "^\\+?[0-9. ()-]{7,25}$",
        message = "Phone number is invalid"
    )
    String phone,

    @NotBlank(
        message = "Email must be present"
    )
    @Email(
        message = "Email is invalid"
    )
    String email
) {

}

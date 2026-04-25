package com.gpsolutions.hotels.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AddressDto(

    @NotNull(
        message = "House number must be present"
    )
    Integer houseNumber,

    @NotBlank(
        message = "Street must be present"
    )
    String street,

    @NotBlank(
        message = "City must be present"
    )
    String city,

    @NotBlank(
        message = "Country must be present"
    )
    String country,

    @NotBlank(
        message = "Postal code must be present"
    )
    String postCode
) {

  @Override
  public String toString() {
    return "%s %s, %s, %s, %s".formatted(
        houseNumber,
        street,
        city,
        postCode,
        country);
  }
}

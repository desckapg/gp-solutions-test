package com.gpsolutions.hotels.domain.dto;

import jakarta.validation.constraints.NotBlank;

public record AddressDto(

    @NotBlank(
        message = "House number must be present"
    )
    String houseNumber,

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
        country,
        postCode);
  }
}

package com.gpsolutions.hotels.domain.dto.request;

import com.gpsolutions.hotels.domain.dto.AddressDto;
import com.gpsolutions.hotels.domain.dto.ArrivalTimeDto;
import com.gpsolutions.hotels.domain.dto.ContactDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record HotelCreateDto(
    @NotBlank(
        message = "Name must be present"
    )
    String name,

    @NotBlank(
        message = "Brand must be present"
    )
    String brand,

    String description,

    @NotNull(
        message = "Address must be present"
    )
    @Valid
    AddressDto address,

    @NotNull(
        message = "Contacts must be present"
    )
    @Valid
    ContactDto contacts,

    @NotNull(
        message = "Arrival time must be present"
    )
    @Valid
    ArrivalTimeDto arrivalTime
) {

}

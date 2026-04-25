package com.gpsolutions.hotels.domain.dto.request;

import com.gpsolutions.hotels.domain.dto.AddressDto;
import com.gpsolutions.hotels.domain.dto.ContactDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

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
    AddressDto address,

    @NotNull(
        message = "Contacts must be present"
    )
    ContactDto contacts,

    @NotNull(
        message = "Check in time must be present"
    )
    LocalTime checkInTime,

    LocalTime checkOutTime
) {

}

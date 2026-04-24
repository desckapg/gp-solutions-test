package com.gpsolutions.hotels.domain.dto.request;

import com.gpsolutions.hotels.domain.dto.AddressDto;
import com.gpsolutions.hotels.domain.dto.ContactDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalTime;
import java.util.List;

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
    @Size(
        min = 1,
        message = "At least one contact must be provided"
    )
    List<ContactDto> contacts,

    @NotNull(
        message = "Check in time must be present"
    )
    LocalTime checkInTime,

    LocalTime checkOutTime
) {

}

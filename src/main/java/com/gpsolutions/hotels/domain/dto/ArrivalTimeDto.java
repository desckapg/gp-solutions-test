package com.gpsolutions.hotels.domain.dto;

import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record ArrivalTimeDto(
    @NotNull(
        message = "Check in time must be present"
    )
    LocalTime checkIn,
    LocalTime checkOut
) {

}

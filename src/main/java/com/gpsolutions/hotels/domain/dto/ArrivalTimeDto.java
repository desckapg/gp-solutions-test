package com.gpsolutions.hotels.domain.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotNull;
import java.time.LocalTime;

public record ArrivalTimeDto(
    @NotNull(
        message = "Check in time must be present"
    )
    @JsonFormat(pattern = "HH:mm")
    LocalTime checkIn,

    @JsonFormat(pattern = "HH:mm")
    LocalTime checkOut
) {

}

package com.gpsolutions.hotels.domain.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record HotelDto(
    Long id,
    String name,
    String brand,
    String description,
    LocalTime checkInTime,
    LocalTime checkOutTime,
    AddressDto address,
    List<AmenityDto> amenities) {

}

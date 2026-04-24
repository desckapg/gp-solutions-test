package com.gpsolutions.hotels.domain.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.gpsolutions.hotels.domain.dto.AddressDto;
import java.time.LocalTime;
import java.util.List;
import lombok.Builder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Builder
public record HotelDto(
    Long id,
    String name,
    String brand,
    String description,
    LocalTime checkInTime,
    LocalTime checkOutTime,
    AddressDto address,
    List<String> amenities) {

}

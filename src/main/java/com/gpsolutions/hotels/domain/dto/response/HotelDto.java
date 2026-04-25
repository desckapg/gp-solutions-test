package com.gpsolutions.hotels.domain.dto.response;

import com.gpsolutions.hotels.domain.dto.AddressDto;
import com.gpsolutions.hotels.domain.dto.ArrivalTimeDto;
import java.util.List;
import lombok.Builder;

@Builder
public record HotelDto(
    Long id,
    String name,
    String brand,
    String description,
    ArrivalTimeDto arrivalTime,
    AddressDto address,
    List<String> amenities) {

}

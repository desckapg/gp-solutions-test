package com.gpsolutions.hotels.domain.dto.response;

import com.gpsolutions.hotels.domain.dto.AddressDto;
import com.gpsolutions.hotels.domain.dto.ArrivalTimeDto;
import com.gpsolutions.hotels.domain.dto.ContactDto;
import java.util.List;
import lombok.Builder;

@Builder
public record HotelDto(
    Long id,
    String name,
    String brand,
    String description,
    ContactDto contacts,
    ArrivalTimeDto arrivalTime,
    AddressDto address,
    List<String> amenities) {

}

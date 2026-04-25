package com.gpsolutions.hotels.domain.dto.response;

public record HotelShortDto(
    Long id,
    String name,
    String description,
    String address,
    String phone
) {

}

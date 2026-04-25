package com.gpsolutions.hotels.domain.mapper;

import com.gpsolutions.hotels.domain.dto.request.HotelCreateDto;
import com.gpsolutions.hotels.domain.dto.response.HotelDto;
import com.gpsolutions.hotels.domain.dto.response.HotelShortDto;
import com.gpsolutions.hotels.domain.entity.Amenity;
import com.gpsolutions.hotels.domain.entity.Hotel;
import java.util.List;
import org.mapstruct.AfterMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(
    componentModel = "spring",
    injectionStrategy = InjectionStrategy.CONSTRUCTOR
)
public interface HotelMapper {

  @Mapping(target = "amenities", expression = "java(mapAmenityNames(hotel))")
  @Mapping(target = "arrivalTime.checkIn", source = "checkInTime")
  @Mapping(target = "arrivalTime.checkOut", source = "checkOutTime")
  HotelDto toDto(Hotel hotel);

  @Mapping(target = "phone", expression = "java(hotel.getContacts().getPhone())")
  @Mapping(target = "address", expression = "java(hotel.getAddress().toString())")
  HotelShortDto toShortDto(Hotel hotel);

  @Mapping(target = "amenities", ignore = true)
  @Mapping(target = "checkInTime", source = "arrivalTime.checkIn")
  @Mapping(target = "checkOutTime", source = "arrivalTime.checkOut")
  Hotel toEntity(HotelCreateDto hotelCreateDto);

  @AfterMapping
  default void linkRelations(@MappingTarget Hotel hotel) {
    hotel.getAddress().setHotel(hotel);
    hotel.getContacts().setHotel(hotel);
  }

  default List<String> mapAmenityNames(Hotel hotel) {
    return hotel.getAmenities().stream().map(Amenity::getName).toList();
  }

}

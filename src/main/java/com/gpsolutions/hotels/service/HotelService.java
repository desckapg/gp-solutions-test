package com.gpsolutions.hotels.service;

import com.gpsolutions.hotels.domain.dto.request.HotelCreateDto;
import com.gpsolutions.hotels.domain.dto.response.HotelDto;
import com.gpsolutions.hotels.domain.dto.request.HotelSpecsDto;
import com.gpsolutions.hotels.domain.dto.response.HotelShortDto;
import java.util.List;

public interface HotelService {

  HotelDto findById(Long id);

  List<HotelShortDto> findAll();

  List<HotelShortDto> findAll(HotelSpecsDto hotelSpecsDto);

  HotelShortDto save(HotelCreateDto hotelDto);

  void addAmenities(Long id, List<String> amenities);

}

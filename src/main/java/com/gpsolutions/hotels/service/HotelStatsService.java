package com.gpsolutions.hotels.service;

import com.gpsolutions.hotels.domain.dto.response.HotelStatsDto;
import java.util.List;

public interface HotelStatsService {

  List<HotelStatsDto> queryCountGroupedByBrand();

  List<HotelStatsDto> queryCountGroupedByAmenities();

  List<HotelStatsDto> queryCountGroupedByCity();

  List<HotelStatsDto> queryCountGroupedByCountry();

}

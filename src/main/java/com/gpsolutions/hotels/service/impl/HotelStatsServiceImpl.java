package com.gpsolutions.hotels.service.impl;

import com.gpsolutions.hotels.domain.dto.response.HotelStatsDto;
import com.gpsolutions.hotels.repository.AddressRepository;
import com.gpsolutions.hotels.repository.AmenityRepository;
import com.gpsolutions.hotels.repository.HotelRepository;
import com.gpsolutions.hotels.service.HotelStatsService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class HotelStatsServiceImpl implements HotelStatsService {

  private final AmenityRepository amenityRepository;
  private final AddressRepository addrRepository;
  private final HotelRepository hotelRepository;

  @Override
  public List<HotelStatsDto> queryCountGroupedByBrand() {
    return hotelRepository.queryHotelCountGroupedByBrand();
  }

  @Override
  public List<HotelStatsDto> queryCountGroupedByAmenities() {
    return amenityRepository.queryHotelCountGroupedByAmenities();
  }

  @Override
  public List<HotelStatsDto> queryCountGroupedByCity() {
    return addrRepository.queryHotelCountGroupedByCity();

  }

  @Override
  public List<HotelStatsDto> queryCountGroupedByCountry() {
    return addrRepository.queryHotelCountGroupedByCountry();

  }

}

package com.gpsolutions.hotels.service.impl;

import com.gpsolutions.hotels.domain.dto.request.HotelCreateDto;
import com.gpsolutions.hotels.domain.dto.request.HotelSpecsDto;
import com.gpsolutions.hotels.domain.dto.response.HotelDto;
import com.gpsolutions.hotels.domain.dto.response.HotelShortDto;
import com.gpsolutions.hotels.domain.entity.Amenity;
import com.gpsolutions.hotels.repository.AmenityRepository;
import com.gpsolutions.hotels.domain.mapper.HotelMapper;
import com.gpsolutions.hotels.exception.ResourceNotFoundException;
import com.gpsolutions.hotels.repository.HotelRepository;
import com.gpsolutions.hotels.service.HotelService;
import java.util.List;
import java.util.Map;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@AllArgsConstructor
@Service
public class HotelServiceImpl implements HotelService {

  private final HotelRepository hotelRepository;
  private final AmenityRepository amenityRepository;
  private final HotelMapper hotelMapper;

  @Override
  @Transactional(readOnly = true)
  public HotelDto findById(Long id) {
    return hotelMapper.toDto(hotelRepository.findById(id)
        .orElseThrow(() -> ResourceNotFoundException.byId("Hotel", id)));
  }

  @Override
  @Transactional(readOnly = true)
  public List<HotelShortDto> findAll() {
    return hotelRepository.findAll().stream()
        .map(hotelMapper::toShortDto)
        .toList();
  }

  @Override
  @Transactional(readOnly = true)
  public List<HotelShortDto> findAll(HotelSpecsDto hotelSpecsDto) {
    return hotelRepository.findAll(hotelSpecsDto.convertToSpecification()).stream()
        .map(hotelMapper::toShortDto)
        .toList();
  }

  @Override
  @Transactional
  public HotelShortDto save(HotelCreateDto hotelDto) {
    return hotelMapper.toShortDto(hotelRepository.save(hotelMapper.toEntity(hotelDto)));
  }

  @Override
  @Transactional
  public void addAmenities(Long id, List<String> amenities) {
    if (!hotelRepository.existsById(id)) {
      throw ResourceNotFoundException.byId("Hotel", id);
    }
    var hotelProxy = hotelRepository.getReferenceById(id);
    var amenityEntities = amenities.stream()
        .map(name -> {
          var a = new Amenity();
          a.setName(name);
          a.setHotel(hotelProxy);
          return a;
        })
        .toList();

    amenityRepository.saveAll(amenityEntities);
  }

  @Override
  public Map<String, Integer> queryCountGroupedByBrand() {
    return hotelRepository.queryCountGroupedByBrand();
  }

  @Override
  public Map<String, Integer> queryCountGroupedByAmenities() {
    return hotelRepository.queryCountGroupedByAmenities();
  }

  @Override
  public Map<String, Integer> queryCountGroupedByCity() {
    return hotelRepository.queryCountGroupedByCity();

  }

  @Override
  public Map<String, Integer> queryCountGroupedByCountry() {
    return hotelRepository.queryCountGroupedByCountry();

  }
}

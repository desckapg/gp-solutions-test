package com.gpsolutions.hotels.controller;

import com.gpsolutions.hotels.domain.dto.response.HotelStatsDto;
import com.gpsolutions.hotels.service.HotelStatsService;
import java.util.Map;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/property-view/histogram")
@RequiredArgsConstructor
public class HistogramController {

  private final HotelStatsService hotelStatsService;

  @GetMapping("/country")
  public ResponseEntity<Map<String, Long>> queryCountGroupedByCountry() {
    return ResponseEntity.ok(hotelStatsService.queryCountGroupedByCountry().stream().collect(
        Collectors.<HotelStatsDto, String, Long>toMap(HotelStatsDto::param, HotelStatsDto::value)));
  }

  @GetMapping("/city")
  public ResponseEntity<Map<String, Long>> queryCountGroupedByCity() {
    return ResponseEntity.ok(hotelStatsService.queryCountGroupedByCity().stream().collect(
        Collectors.<HotelStatsDto, String, Long>toMap(HotelStatsDto::param, HotelStatsDto::value)));
  }

  @GetMapping("/brand")
  public ResponseEntity<Map<String, Long>> queryCountGroupedByBrand() {
    return ResponseEntity.ok(hotelStatsService.queryCountGroupedByBrand().stream().collect(
        Collectors.<HotelStatsDto, String, Long>toMap(HotelStatsDto::param, HotelStatsDto::value)));
  }

  @GetMapping("/amenities")
  public ResponseEntity<Map<String, Long>> queryCountGroupedByAmenities() {
    return ResponseEntity.ok(hotelStatsService.queryCountGroupedByAmenities().stream().collect(
        Collectors.<HotelStatsDto, String, Long>toMap(HotelStatsDto::param, HotelStatsDto::value)));
  }

}

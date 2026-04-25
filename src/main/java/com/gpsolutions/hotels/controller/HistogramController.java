package com.gpsolutions.hotels.controller;

import com.gpsolutions.hotels.service.HotelService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/property-view/histogram")
@RequiredArgsConstructor
public class HistogramController {

  private final HotelService hotelService;

  @GetMapping("/country")
  public ResponseEntity<Map<String, Integer>> queryCountGroupedByCountry() {
    return ResponseEntity.ok(hotelService.queryCountGroupedByCountry());
  }

  @GetMapping("/city")
  public ResponseEntity<Map<String, Integer>> queryCountGroupedByCity() {
    return ResponseEntity.ok(hotelService.queryCountGroupedByCity());
  }

  @GetMapping("/brand")
  public ResponseEntity<Map<String, Integer>> queryCountGroupedByBrand() {
    return ResponseEntity.ok(hotelService.queryCountGroupedByBrand());
  }

  @GetMapping("/amenities")
  public ResponseEntity<Map<String, Integer>> queryCountGroupedByAmenities() {
    return ResponseEntity.ok(hotelService.queryCountGroupedByAmenities());
  }

}

package com.gpsolutions.hotels.controller;

import com.gpsolutions.hotels.domain.dto.request.HotelSpecsDto;
import com.gpsolutions.hotels.domain.dto.response.HotelShortDto;
import com.gpsolutions.hotels.service.HotelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/property-view/search")
@RequiredArgsConstructor
public class SearchController {

  private final HotelService hotelService;

  @GetMapping
  public ResponseEntity<List<HotelShortDto>> findAllByCriteria(
      @RequestParam(required = false) String name,
      @RequestParam(required = false) String city,
      @RequestParam(required = false) String brand,
      @RequestParam(required = false) String country,
      @RequestParam(required = false) List<String> amenities
  ) {
    return ResponseEntity.ok(hotelService.findAll(new HotelSpecsDto(name, city, brand, country, amenities)));
  }

}

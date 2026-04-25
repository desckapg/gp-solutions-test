package com.gpsolutions.hotels.controller;

import com.gpsolutions.hotels.domain.dto.request.HotelCreateDto;
import com.gpsolutions.hotels.domain.dto.response.HotelDto;
import com.gpsolutions.hotels.domain.dto.response.HotelShortDto;
import com.gpsolutions.hotels.service.HotelService;
import java.net.URI;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/property-view/hotels")
public class HotelController {

  private final HotelService hotelService;

  @GetMapping
  public ResponseEntity<List<HotelShortDto>> findAll() {
    return ResponseEntity.ok(hotelService.findAll());
  }

  @GetMapping("/{id}")
  public ResponseEntity<HotelDto> findById(@PathVariable Long id) {
    return ResponseEntity.ok(hotelService.findById(id));
  }

  @PostMapping
  public ResponseEntity<HotelShortDto> create(
      @RequestBody @Validated HotelCreateDto hotelCreateDto) {
    var hotel = hotelService.save(hotelCreateDto);
    return ResponseEntity.created(URI.create("/property-view/hotels/" + hotel.id())).body(hotel);
  }

  @PostMapping("/{id}/amenities")
  public ResponseEntity<Void> addAmenities(@PathVariable Long id,
      @RequestBody List<String> amenities) {
    hotelService.addAmenities(id, amenities);
    return ResponseEntity.created(URI.create("/property-view/hotels/" + id)).build();
  }

}

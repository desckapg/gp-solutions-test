package com.gpsolutions.hotels.repository;

import com.gpsolutions.hotels.domain.dto.response.HotelStatsDto;
import com.gpsolutions.hotels.domain.entity.Amenity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

  @Query("SELECT a.name, COUNT(*) FROM Amenity a GROUP BY a.name")
  List<HotelStatsDto> queryHotelCountGroupedByAmenities();

}

package com.gpsolutions.hotels.repository;

import com.gpsolutions.hotels.domain.entity.Hotel;
import java.util.Map;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

  @Query("SELECT a.city, COUNT(a.hotel.id) FROM Address a GROUP BY a.city")
  Map<String, Integer> queryCountGroupedByCity();

  @Query("SELECT a.country, COUNT(a.hotel.id) FROM Address a GROUP BY a.country")
  Map<String, Integer> queryCountGroupedByCountry();

  @Query("SELECT h.brand, COUNT(*) FROM Hotel h GROUP BY h.brand")
  Map<String, Integer> queryCountGroupedByBrand();

  @Query("SELECT a.name, COUNT(*) FROM Amenity a GROUP BY a.name")
  Map<String, Integer> queryCountGroupedByAmenities();

}

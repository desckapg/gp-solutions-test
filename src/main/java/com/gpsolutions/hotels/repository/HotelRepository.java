package com.gpsolutions.hotels.repository;

import com.gpsolutions.hotels.domain.dto.response.HotelStatsDto;
import com.gpsolutions.hotels.domain.entity.Hotel;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

public interface HotelRepository extends JpaRepository<Hotel, Long>, JpaSpecificationExecutor<Hotel> {

  @Query("SELECT h.brand, COUNT(*) FROM Hotel h GROUP BY h.brand")
  List<HotelStatsDto> queryHotelCountGroupedByBrand();

}

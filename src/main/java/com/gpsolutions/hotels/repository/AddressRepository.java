package com.gpsolutions.hotels.repository;

import com.gpsolutions.hotels.domain.dto.response.HotelStatsDto;
import com.gpsolutions.hotels.domain.entity.Address;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface AddressRepository extends JpaRepository<Address, Long> {

  @Query("SELECT a.city, COUNT(a.hotel.id) FROM Address a GROUP BY a.city")
  List<HotelStatsDto> queryHotelCountGroupedByCity();

  @Query("SELECT a.country, COUNT(a.hotel.id) FROM Address a GROUP BY a.country")
  List<HotelStatsDto> queryHotelCountGroupedByCountry();

}

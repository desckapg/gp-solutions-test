package com.gpsolutions.hotels.repository;

import com.gpsolutions.hotels.domain.entity.Hotel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HotelRepository extends JpaRepository<Hotel, Long> {

}

package com.gpsolutions.hotels.repository;

import com.gpsolutions.hotels.domain.entity.Amenity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AmenityRepository extends JpaRepository<Amenity, Long> {

}

package com.gpsolutions.hotels.integration.service;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpsolutions.hotels.integration.AbstractIntegrationTest;
import com.gpsolutions.hotels.service.HotelStatsService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;

@RequiredArgsConstructor
class HotelStatsServiceIT extends AbstractIntegrationTest {

  private final HotelStatsService hotelStatsService;
  private final EntityManager tem;

  @Test
  void queryCountGroupedByBrand_dataExists_shouldCollectStatistics() {
    // when
    var stats = hotelStatsService.queryCountGroupedByBrand();

    // then
    assertThat(stats)
        .isNotEmpty()
        .allSatisfy(stat -> assertThat(stat.value()).isEqualTo(
            tem.createQuery("SELECT COUNT(*) FROM Hotel WHERE brand = :brand")
                .setParameter("brand", stat.param())
                .getSingleResult()));
  }

  @Test
  void queryCountGroupedByAmenities_dataExists_shouldCollectStatistics() {
    // when
    var stats = hotelStatsService.queryCountGroupedByAmenities();

    // then
    assertThat(stats)
        .isNotEmpty()
        .allSatisfy(stat -> assertThat(stat.value()).isEqualTo(
            tem.createQuery("SELECT COUNT(a.hotel.id) FROM Amenity a WHERE a.name = :name")
                .setParameter("name", stat.param())
                .getSingleResult()));
  }

  @Test
  void queryCountGroupedByCity_dataExists_shouldCollectStatistics() {
    // when
    var stats = hotelStatsService.queryCountGroupedByCity();

    // then
    assertThat(stats)
        .isNotEmpty()
        .allSatisfy(stat -> assertThat(stat.value()).isEqualTo(
            tem.createQuery("SELECT COUNT(a.hotel.id) FROM Address a WHERE a.city = :city")
                .setParameter("city", stat.param())
                .getSingleResult()));
  }

  @Test
  void queryCountGroupedByCountry_dataExists_shouldCollectStatistics() {
    // when
    var stats = hotelStatsService.queryCountGroupedByCountry();

    // then
    assertThat(stats)
        .isNotEmpty()
        .allSatisfy(stat -> assertThat(stat.value()).isEqualTo(
            tem.createQuery("SELECT COUNT(*) FROM Address a WHERE a.country = :country")
                .setParameter("country", stat.param())
                .getSingleResult()));
  }

}

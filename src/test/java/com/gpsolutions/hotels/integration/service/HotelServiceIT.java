package com.gpsolutions.hotels.integration.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import com.gpsolutions.hotels.domain.dto.AddressDto;
import com.gpsolutions.hotels.domain.dto.ArrivalTimeDto;
import com.gpsolutions.hotels.domain.dto.ContactDto;
import com.gpsolutions.hotels.domain.dto.request.HotelCreateDto;
import com.gpsolutions.hotels.domain.dto.request.HotelSpecsDto;
import com.gpsolutions.hotels.domain.dto.response.HotelShortDto;
import com.gpsolutions.hotels.domain.entity.Amenity;
import com.gpsolutions.hotels.domain.entity.Hotel;
import com.gpsolutions.hotels.exception.ResourceNotFoundException;
import com.gpsolutions.hotels.integration.AbstractIntegrationTest;
import com.gpsolutions.hotels.integration.IT;
import com.gpsolutions.hotels.service.HotelService;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.boot.jpa.test.autoconfigure.TestEntityManager;

@IT
@AllArgsConstructor
class HotelServiceIT extends AbstractIntegrationTest {

  private static final HotelCreateDto CREATE_DTO = new HotelCreateDto(
      "Integration Test Hotel",
      "Integration Brand",
      "Integration test description",
      new AddressDto(77, "Test Street", "Test City", "Test Country", "77777"),
      new ContactDto("+1 (555) 700-0001", "integration-hotel@mail.com"),
      new ArrivalTimeDto(LocalTime.of(14, 0), LocalTime.of(11, 0))
  );

  private final HotelService hotelService;
  private final TestEntityManager entityManager;

  @Test
  void findById_hotelExists_returnDetailedDto() {
    // when
    var result = hotelService.findById(EXISTING_HOTEL_ID);

    // then
    assertThat(result).isNotNull();
    assertThat(result.id()).isEqualTo(EXISTING_HOTEL_ID);
    assertThat(result.name()).isEqualTo(EXISTING_HOTEL_NAME);
    assertThat(result.brand()).isEqualTo(EXISTING_HOTEL_BRAND);
    assertThat(result.description()).isEqualTo(EXISTING_HOTEL_DESCRIPTION);
    assertThat(result.arrivalTime().checkIn()).isEqualTo(LocalTime.of(14, 0));
    assertThat(result.arrivalTime().checkOut()).isEqualTo(LocalTime.of(11, 0));
    assertThat(result.address().houseNumber()).isEqualTo(EXISTING_HOUSE_NUMBER);
    assertThat(result.address().street()).isEqualTo(EXISTING_STREET);
    assertThat(result.address().city()).isEqualTo(EXISTING_CITY);
    assertThat(result.address().country()).isEqualTo(EXISTING_COUNTRY);
    assertThat(result.address().postCode()).isEqualTo(EXISTING_POSTAL_CODE);
    assertThat(result.amenities()).containsAll(EXISTING_HOTEL_AMENITIES);
  }

  @Test
  void findById_hotelDoesNotExist_throwResourceNotFound() {
    // when / then
    assertThatExceptionOfType(ResourceNotFoundException.class)
        .isThrownBy(() -> hotelService.findById(UNKNOWN_HOTEL_ID));
  }

  @Test
  void findAll_seedDataLoaded_returnShortDtos() {
    // when
    var result = hotelService.findAll();

    // then
    assertThat(result).hasSize(SEEDED_HOTELS_COUNT);

    var firstHotel = result.stream()
        .filter(dto -> dto.id().equals(EXISTING_HOTEL_ID))
        .findFirst()
        .orElseThrow();

    assertThat(firstHotel.name()).isEqualTo(EXISTING_HOTEL_NAME);
    assertThat(firstHotel.description()).isEqualTo(EXISTING_HOTEL_DESCRIPTION);
    assertThat(firstHotel.address())
        .isEqualTo("1 Main Street, New York, 10001, United States");
    assertThat(firstHotel.phone()).isIn("(555) 100-2345", "+1-555-111-0001");
  }

  @Test
  void findAllBySpecs_filterByNameBrandAmenities_returnMatchingHotels() {
    // given
    var specs = new HotelSpecsDto(
        EXISTING_HOTEL_NAME,
        EXISTING_HOTEL_BRAND,
        null,
        null,
        List.of("Swimming Pool")
    );

    // when
    var result = hotelService.findAll(specs);

    // then
    assertThat(result).isNotEmpty();
    assertThat(result).extracting(HotelShortDto::id).contains(EXISTING_HOTEL_ID);
  }

  @Test
  void findAllBySpecs_whenNoMatches_returnEmptyList() {
    // given
    var specs = new HotelSpecsDto("Definitely unknown hotel", null, null, null,
        List.of("Definitely unknown amenity"));

    // when
    var result = hotelService.findAll(specs);

    // then
    assertThat(result).isEmpty();
  }

  @Test
  void save_validHotelCreateDto_persistAndReturnShortDto() {
    // given
    var dto = CREATE_DTO;

    // when
    var result = hotelService.save(dto);

    entityManager.flush();
    entityManager.clear();

    // then
    assertThat(result.id()).isNotNull();
    assertThat(result.name()).isEqualTo(dto.name());
    assertThat(result.phone()).isEqualTo(dto.contacts().phone());

    var persistedHotel = entityManager.find(Hotel.class, result.id());
    assertThat(persistedHotel).isNotNull();
    assertThat(persistedHotel.getAddress().getCity()).isEqualTo(dto.address().city());
  }

  @Test
  void addAmenities_hotelExists_addAmenitiesToHotel() {
    // given
    var hotelId = EXISTING_HOTEL_ID;
    var newAmenities = List.of("IT Test Amenity One", "IT Test Amenity Two");
    var countBefore = countHotelAmenities(hotelId);

    // when
    hotelService.addAmenities(hotelId, newAmenities);

    entityManager.flush();
    entityManager.clear();

    // then
    var countAfter = countHotelAmenities(hotelId);
    assertThat(countAfter).isEqualTo(countBefore + 2);

    var added = loadAmenitiesByHotelId(hotelId).stream()
        .map(Amenity::getName)
        .filter(newAmenities::contains)
        .toList();
    assertThat(added).containsExactlyInAnyOrderElementsOf(newAmenities);
  }

  @Test
  void addAmenities_emptyList_doNothing() {
    // given
    var hotelId = EXISTING_HOTEL_ID;
    var countBefore = countHotelAmenities(hotelId);

    // when
    hotelService.addAmenities(hotelId, List.of());

    entityManager.flush();
    entityManager.clear();

    // then
    var countAfter = countHotelAmenities(hotelId);
    assertThat(countAfter).isEqualTo(countBefore);
  }

  @Test
  void addAmenities_hotelDoesNotExist_throwResourceNotFound() {
    // given
    var unknownId = UNKNOWN_HOTEL_ID;

    // when / then
    assertThatExceptionOfType(ResourceNotFoundException.class)
        .isThrownBy(() -> hotelService.addAmenities(unknownId, List.of("WiFi")))
        .withMessage("Hotel with id %d not found".formatted(unknownId));
  }

  @Test
  void addAmenities_duplicatesProvided_persistDuplicates() {
    // given
    var hotelId = EXISTING_HOTEL_ID;
    var duplicatedAmenityName = "IT Test Duplicate Amenity";
    var countByNameBefore = countHotelAmenitiesByName(hotelId, duplicatedAmenityName);

    // when
    hotelService.addAmenities(hotelId, List.of(duplicatedAmenityName, duplicatedAmenityName));

    entityManager.flush();
    entityManager.clear();

    // then
    var countByNameAfter = countHotelAmenitiesByName(hotelId, duplicatedAmenityName);
    assertThat(countByNameAfter).isEqualTo(countByNameBefore + 2);
  }

  private long countHotelAmenities(Long hotelId) {
    return entityManager.getEntityManager()
        .createQuery(
            "select count(a) from Amenity a where a.hotel.id = :hotelId", Long.class)
        .setParameter("hotelId", hotelId)
        .getSingleResult();
  }

  private long countHotelAmenitiesByName(Long hotelId, String amenityName) {
    return entityManager.getEntityManager()
        .createQuery(
            "select count(a) from Amenity a where a.hotel.id = :hotelId and a.name = :name",
            Long.class)
        .setParameter("hotelId", hotelId)
        .setParameter("name", amenityName)
        .getSingleResult();
  }

  private List<Amenity> loadAmenitiesByHotelId(Long hotelId) {
    return entityManager.getEntityManager()
        .createQuery("select a from Amenity a where a.hotel.id = :hotelId", Amenity.class)
        .setParameter("hotelId", hotelId)
        .getResultList();
  }
}

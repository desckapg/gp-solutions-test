package com.gpsolutions.hotels.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpsolutions.hotels.integration.AbstractIntegrationTest;
import com.gpsolutions.hotels.service.HotelService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import net.jqwik.api.Arbitraries;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@RequiredArgsConstructor
class HotelControllerIT extends AbstractIntegrationTest {

  private final MockMvcTester mockMvc;
  private final HotelService hotelService;

  @Test
  void findById_hotelWithRandomIdExists_returnDetailedDto() {
    // given
    var id = Arbitraries.integers().between(1, SEEDED_HOTELS_COUNT).sample();

    // when / then
    assertThat(mockMvc.get().uri("/property-view/hotels/" + id)).hasStatusOk();
  }

  @Test
  void findById_hotelWithConcreteIdExists_returnDetailedDto() {
    // when
    var result = mockMvc.get().uri("/property-view/hotels/" + EXISTING_HOTEL_ID);

    // then
    assertThat(result).hasStatusOk();
    assertThat(result).bodyJson().extractingPath("$.id").convertTo(Long.class)
        .isEqualTo(EXISTING_HOTEL_ID);
    assertThat(result).bodyJson().extractingPath("$.name").asString()
        .isEqualTo(EXISTING_HOTEL_NAME);
    assertThat(result).bodyJson().extractingPath("$.brand").asString()
        .isEqualTo(EXISTING_HOTEL_BRAND);
    assertThat(result).bodyJson().extractingPath("$.description").asString()
        .isEqualTo(EXISTING_HOTEL_DESCRIPTION);
    assertThat(result).bodyJson()
        .hasPath("$.address.houseNumber")
        .hasPath("$.address.city")
        .hasPath("$.address.country")
        .hasPath("$.address.postCode")
        .hasPath("$.address.street")
        .hasPath("$.arrivalTime.checkIn")
        .hasPath("$.amenities");
  }

  @Test
  void findById_hotelWithConcreteIdExists_returnSeededAddressValues() {
    // when
    var result = mockMvc.get().uri("/property-view/hotels/" + EXISTING_HOTEL_ID);

    // then
    assertThat(result).hasStatusOk();
    assertThat(result).bodyJson().extractingPath("$.address.houseNumber").asNumber()
        .isEqualTo(EXISTING_HOUSE_NUMBER);
    assertThat(result).bodyJson().extractingPath("$.address.street").asString()
        .isEqualTo(EXISTING_STREET);
    assertThat(result).bodyJson().extractingPath("$.address.city").asString()
        .isEqualTo(EXISTING_CITY);
    assertThat(result).bodyJson().extractingPath("$.address.country").asString()
        .isEqualTo(EXISTING_COUNTRY);
    assertThat(result).bodyJson().extractingPath("$.address.postCode").asString()
        .isEqualTo(EXISTING_POSTAL_CODE);
  }

  @Test
  void findById_hotelDoesNotExist_returnNotFoundError() {
    // when
    var result = mockMvc.get().uri("/property-view/hotels/" + UNKNOWN_HOTEL_ID);

    // then
    assertThat(result).hasStatus(404);
    assertThat(result).bodyJson().extractingPath("$.status").convertTo(Integer.class)
        .isEqualTo(404);
    assertThat(result).bodyJson().extractingPath("$.title").asString().isEqualTo("Not Found");
    assertThat(result).bodyJson().extractingPath("$.detail").asString()
        .isEqualTo("Hotel with id %d not found".formatted(UNKNOWN_HOTEL_ID));
  }

  @Test
  void findAll_hotelsExist_returnShortDtos() {
    // when
    var result = mockMvc.get().uri("/property-view/hotels");

    // then
    assertThat(result)
        .hasStatusOk()
        .bodyJson()
        .hasPath("$[0].id")
        .hasPath("$[0].name")
        .hasPath("$[0].description")
        .hasPath("$[0].address")
        .hasPath("$[0].phone");
  }

  @Test
  void create_validPayload_returnCreatedShortDto() {
    // given
    var body = """
        {
          "name": "DoubleTree by Hilton Minsk",
          "brand": "Hilton",
          "description": "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian capital and stunning views of Minsk city from the hotel's 20th floor ...",
          "address":
        	{
        		"houseNumber": 9,
        		"street": "Pobediteley Avenue",
        		"city": "Minsk",
        		"country": "Belarus",
        		"postCode": "220004"
        	},
          "contacts":
        	{
        		"phone": "+375 17 309-80-00",
        		"email": "doubletreeminsk.info@hilton.com"
        	},
          "arrivalTime":
        	{
        		"checkIn": "14:00",
        		"checkOut": "12:00"
        	}
        }
        """;

    // when
    var result = mockMvc.post().uri("/property-view/hotels")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body);

    // then
    assertThat(result).hasStatus(201);

  }

  @Test
  void addAmenities_hotelExists_returnCreatedAndAmenitiesAreVisible() {
    // given
    var newAmenities = List.of("Free parking",
        "Free WiFi");
    var body = """
        ["%s", "%s"]
        """.formatted(newAmenities.get(0), newAmenities.get(1));

    // when
    var addResult = mockMvc.post().uri("/property-view/hotels/" + EXISTING_HOTEL_ID + "/amenities")
        .contentType(MediaType.APPLICATION_JSON)
        .content(body)
        .exchange();

    var hotelAfterUpdate = hotelService.findById(EXISTING_HOTEL_ID);

    // then
    assertThat(addResult).hasStatus(201);
    assertThat(hotelAfterUpdate.amenities()).containsAll(newAmenities);
  }

}

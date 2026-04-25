package com.gpsolutions.hotels.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpsolutions.hotels.domain.dto.response.HotelShortDto;
import com.gpsolutions.hotels.integration.AbstractIntegrationTest;
import java.io.UnsupportedEncodingException;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@RequiredArgsConstructor
class SearchControllerIT extends AbstractIntegrationTest {

  private final MockMvcTester mockMvc;

  @Test
  void findAllByCriteria_byCityResultExists_returnListOfHotels() {
    // given
    var city = "New York";

    // when
    var result = mockMvc.get().uri("/property-view/search").param("city", city).exchange();

    // then
    assertThat(result)
        .hasStatusOk()
        .bodyJson()
        .extractingPath("$")
        .asInstanceOf(InstanceOfAssertFactories.list(HotelShortDto.class))
        .isNotEmpty();
  }

  @Test
  void findAllByCriteria_byCityInDifferentLowerCaseResultExists_returnListOfHotels()
      throws UnsupportedEncodingException {
    // given
    var city1 = "New York";
    var city2 = "new york";

    // when
    var result1 = mockMvc.get().uri("/property-view/search").param("city", city1).exchange();
    var result2 = mockMvc.get().uri("/property-view/search").param("city", city2).exchange();

    // then
    assertThat(result1).hasStatusOk();
    assertThat(result2).hasStatusOk();
    assertThat(result1).bodyJson().isEqualTo(result2.getResponse().getContentAsString());
  }

  @Test
  void findAllByCriteria_byCityAndBrandResultExists_returnListOfHotels() {
    // given
    var city = "New York";
    var brand = "GrandVista Hotels";

    // when
    var result = mockMvc.get().uri("/property-view/search")
        .param("city", city)
        .param("brand", brand)
        .exchange();

    // then
    assertThat(result)
        .hasStatusOk()
        .bodyJson()
        .extractingPath("$")
        .asInstanceOf(InstanceOfAssertFactories.list(HotelShortDto.class))
        .isNotEmpty();
  }

  @Test
  void findAllByCriteria_byCityAndBrandResultNotExist_returnEmptyList() {
    // given
    var city = "New York";
    var brand = "Aspire Hotels";

    // when
    var result = mockMvc.get().uri("/property-view/search")
        .param("city", city)
        .param("brand", brand)
        .exchange();

    // then
    assertThat(result)
        .hasStatusOk()
        .bodyJson()
        .extractingPath("$")
        .asInstanceOf(InstanceOfAssertFactories.list(HotelShortDto.class))
        .isEmpty();
  }

}

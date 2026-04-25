package com.gpsolutions.hotels.integration.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.gpsolutions.hotels.integration.AbstractIntegrationTest;
import java.net.URI;
import lombok.RequiredArgsConstructor;
import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.springframework.test.web.servlet.assertj.MockMvcTester;

@RequiredArgsConstructor
class HistogramControllerIT extends AbstractIntegrationTest {

  private final MockMvcTester mockMvc;


  @ParameterizedTest
  @ValueSource(strings = {"brand", "amenities", "city", "country"})
  void queryCountGroupedBy_dataExists_shouldCollectStatistics(String category) {
    // when
    var uri = URI.create("/property-view/histogram/" + category);
    var result = mockMvc.get().uri(uri);

    // then
    assertThat(result)
        .hasStatusOk()
        .bodyJson()
        .hasPath("$")
        .extractingPath("$")
        .asInstanceOf(InstanceOfAssertFactories.map(String.class, Long.class))
        .isNotEmpty();
  }

}

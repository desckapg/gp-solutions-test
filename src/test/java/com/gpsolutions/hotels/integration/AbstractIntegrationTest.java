package com.gpsolutions.hotels.integration;

import java.util.List;

@IT
public abstract class AbstractIntegrationTest {

  protected static final long EXISTING_HOTEL_ID = 1L;
  protected static final long UNKNOWN_HOTEL_ID = Long.MAX_VALUE;
  protected static final int SEEDED_HOTELS_COUNT = 1000;

  protected static final String EXISTING_HOTEL_NAME = "Grand Pacific Hotel";
  protected static final String EXISTING_HOTEL_BRAND = "GrandVista Hotels";
  protected static final String EXISTING_HOTEL_DESCRIPTION =
      "upscale haven — close to the university campus";
  protected static final Integer EXISTING_HOUSE_NUMBER = 1;
  protected static final String EXISTING_STREET = "Main Street";
  protected static final String EXISTING_CITY = "New York";
  protected static final String EXISTING_COUNTRY = "United States";
  protected static final String EXISTING_POSTAL_CODE = "10001";

  protected static final List<String> EXISTING_HOTEL_AMENITIES = List.of(
      "Swimming Pool",
      "Putting Green",
      "High Ceilings"
  );
}

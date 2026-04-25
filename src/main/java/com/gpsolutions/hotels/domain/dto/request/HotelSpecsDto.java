package com.gpsolutions.hotels.domain.dto.request;

import com.gpsolutions.hotels.domain.entity.Hotel;
import java.util.LinkedList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

public record HotelSpecsDto(
    String name,
    String brand,
    String city,
    String country,
    List<String> amenities) {

  public Specification<Hotel> convertToSpecification() {
    List<Specification<Hotel>> specifications = new LinkedList<>();

    if (StringUtils.hasText(name)) {
      specifications.add((root, query, builder) ->
          builder.like(builder.lower(root.get("name")), "%" + name.toLowerCase() + "%"));
    }

    if (StringUtils.hasText(brand)) {
      specifications.add((root, query, builder) ->
          builder.equal(builder.lower(root.get("brand")), brand.toLowerCase()));
    }

    if (StringUtils.hasText(city)) {
      specifications.add((root, query, builder) ->
          builder.equal(builder.lower(root.join("address").get("city")), city.toLowerCase()));
    }

    if (StringUtils.hasText(country)) {
      specifications.add((root, query, builder) ->
          builder.equal(builder.lower(root.join("address").get("country")), country.toLowerCase()));
    }

    if (amenities != null && !amenities.isEmpty()) {
      List<String> lowerAmenities = amenities.stream()
          .map(String::toLowerCase)
          .toList();

      specifications.add((root, query, builder) ->
          builder.lower(root.join("amenities").get("name")).in(lowerAmenities));
    }

    return Specification.allOf(specifications);
  }

}

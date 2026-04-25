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
      specifications.add((root, query, builder) -> builder.like(root.get("name"), "%" + name + "%"));
    }

    if (StringUtils.hasText(brand)) {
      specifications.add((root, query, builder) -> builder.equal(root.get("brand"), brand));
    }

    if (StringUtils.hasText(city)) {
      specifications.add((root, query, builder) -> builder.equal(root.get("city"), city));
    }

    if (StringUtils.hasText(country)) {
      specifications.add((root, query, builder) -> builder.equal(root.get("country"), country));
    }

    if (amenities != null && !amenities.isEmpty()) {
      specifications.add((root, query, builder) -> root.join("amenities").get("name").in(amenities));
    }

    return Specification.allOf(specifications);
  }

}

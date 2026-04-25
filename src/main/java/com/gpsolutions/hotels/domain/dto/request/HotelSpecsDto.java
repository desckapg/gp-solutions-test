package com.gpsolutions.hotels.domain.dto.request;

import com.gpsolutions.hotels.domain.entity.Hotel;
import java.util.LinkedList;
import java.util.List;
import org.springframework.data.jpa.domain.Specification;

public record HotelSpecsDto(
    String name,
    String brand,
    String city,
    String country,
    List<String> amenities) {

  public Specification<Hotel> convertToSpecification() {
    List<Specification<Hotel>> specifications = new LinkedList<>();

    if (name != null) {
      specifications.add((root, query, builder) -> builder.like(root.get("name"), "%" + name + "%"));
    }

    if (brand != null) {
      specifications.add((root, query, builder) -> builder.equal(root.get("brand"), brand));
    }

    if (city != null) {
      specifications.add((root, query, builder) -> builder.equal(root.get("city"), city));
    }

    if (country != null) {
      specifications.add((root, query, builder) -> builder.equal(root.get("country"), country));
    }

    if (amenities != null) {
      specifications.add((root, query, builder) -> root.join("amenities").get("name").in(amenities));
    }

    return Specification.allOf(specifications);
  }

}

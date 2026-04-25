package com.gpsolutions.hotels.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "amenities")
public class Amenity extends BaseEntity<Long> {

  @Column(name = "name")
  private String name;

  @ManyToOne(fetch = FetchType.LAZY)
  private Hotel hotel;

}

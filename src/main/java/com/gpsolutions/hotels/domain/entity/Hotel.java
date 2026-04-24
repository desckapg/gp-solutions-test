package com.gpsolutions.hotels.domain.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "hotels")
public class Hotel extends BaseEntity<Long> {

  @Column(name = "name")
  private String name;

  @Column(name = "brand")
  private String brand;

  @Column(name = "description")
  private String description;

  @Column(name = "check_in_time")
  private LocalTime checkInTime;

  @Column(name = "check_out_time")
  private LocalTime checkOutTime;

  @OneToOne(mappedBy = "hotel", cascade = CascadeType.ALL, orphanRemoval = true)
  private Address address;

  @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
  @JoinColumn(name = "hotel_id")
  private List<Amenity> amenities;

  @Override
  public final boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null) {
      return false;
    }
    Class<?> oEffectiveClass =
        o instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer()
                                            .getPersistentClass() : o.getClass();
    Class<?> thisEffectiveClass =
        this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer()
                                               .getPersistentClass() : this.getClass();
    if (thisEffectiveClass != oEffectiveClass) {
      return false;
    }
    Hotel hotel = (Hotel) o;
    return getId() != null && Objects.equals(getId(), hotel.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer()
                                                  .getPersistentClass().hashCode()
        : getClass().hashCode();
  }

}

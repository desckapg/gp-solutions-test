package com.gpsolutions.hotels.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "addresses")
public class Address extends BaseEntity<Long> {

  @Column(name = "house_number")
  private String houseNumber;

  @Column(name = "street")
  private String street;

  @Column(name = "city")
  private String city;

  @Column(name = "country")
  private String country;

  @Column(name = "postal_code")
  private String postalCode;

  @OneToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "hotel_id")
  private Hotel hotel;

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
    Address address = (Address) o;
    return getId() != null && Objects.equals(getId(), address.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer()
                                                  .getPersistentClass().hashCode()
        : getClass().hashCode();
  }

  @Override
  public String toString() {
    return "%s %s, %s, %s, %s".formatted(
        houseNumber,
        street,
        city,
        country,
        postalCode);
  }

}

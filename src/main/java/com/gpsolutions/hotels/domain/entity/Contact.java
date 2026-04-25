package com.gpsolutions.hotels.domain.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.proxy.HibernateProxy;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "contacts")
public class Contact extends BaseEntity<Long> {

  @Column(name = "phone")
  private String phone;

  @Column(name = "email")
  private String email;

  @OneToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "hotel_id", nullable = false)
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
    Contact contact = (Contact) o;
    return getId() != null && Objects.equals(getId(), contact.getId());
  }

  @Override
  public final int hashCode() {
    return this instanceof HibernateProxy proxy ? proxy.getHibernateLazyInitializer()
                                                  .getPersistentClass().hashCode()
        : getClass().hashCode();
  }

}

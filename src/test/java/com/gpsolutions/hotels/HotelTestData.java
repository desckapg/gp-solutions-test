package com.gpsolutions.hotels;

import com.gpsolutions.hotels.domain.dto.AddressDto;
import com.gpsolutions.hotels.domain.dto.ArrivalTimeDto;
import com.gpsolutions.hotels.domain.dto.ContactDto;
import com.gpsolutions.hotels.domain.dto.request.HotelCreateDto;
import com.gpsolutions.hotels.domain.dto.response.HotelDto;
import com.gpsolutions.hotels.domain.dto.response.HotelShortDto;
import com.gpsolutions.hotels.domain.entity.Address;
import com.gpsolutions.hotels.domain.entity.Amenity;
import com.gpsolutions.hotels.domain.entity.Contact;
import com.gpsolutions.hotels.domain.entity.Hotel;
import java.time.LocalTime;
import java.util.LinkedList;
import java.util.List;

public class HotelTestData {

  public static final String DEFAULT_NAME = "DoubleTree by Hilton Minsk";
  public static final String DEFAULT_BRAND = "Hilton";
  public static final String DEFAULT_CITY = "Minsk";
  public static final String DEFAULT_COUNTRY = "Belarus";
  public static final String DEFAULT_PHONE = "+375 17 309-80-00";
  public static final String DEFAULT_EMAIL = "minsk.info@hilton.com";

  private HotelTestData() {
  }

  public static Hotel createDefaultHotel() {
    Hotel hotel = new Hotel();
    hotel.setName(DEFAULT_NAME);
    hotel.setDescription(
        "The DoubleTree by Hilton Hotel Minsk offers 193 luxurious rooms in the Belorussian"
            + " capital and stunning views of Minsk city from the hotel's 20th floor ..."
    );
    hotel.setBrand(DEFAULT_BRAND);
    hotel.setCheckInTime(LocalTime.of(14, 0));
    hotel.setCheckOutTime(LocalTime.of(12, 0));
    hotel.setAmenities(new LinkedList<>());

    Address address = new Address();
    address.setHouseNumber(9);
    address.setStreet("Pobediteley Avenue");
    address.setCity(DEFAULT_CITY);
    address.setCountry(DEFAULT_COUNTRY);
    address.setPostCode("220004");
    address.setHotel(hotel);
    hotel.setAddress(address);

    Contact contact = new Contact();
    contact.setPhone(DEFAULT_PHONE);
    contact.setEmail(DEFAULT_EMAIL);
    contact.setHotel(hotel);
    hotel.setContacts(contact);

    return hotel;
  }

  public static HotelCreateDto createDefaultHotelCreateDto() {
    return new HotelCreateDto(
        DEFAULT_NAME,
        DEFAULT_BRAND,
        "Test description",
        new AddressDto(9, "Pobediteley Avenue", DEFAULT_CITY, DEFAULT_COUNTRY, "220004"),
        new ContactDto(DEFAULT_PHONE, DEFAULT_EMAIL),
        new ArrivalTimeDto(LocalTime.of(14, 0), LocalTime.of(12, 0))
    );
  }

  public static HotelDto createDefaultHotelDto(Long id) {
    return new HotelDto(
        id,
        DEFAULT_NAME,
        DEFAULT_BRAND,
        "Test description",
        new ArrivalTimeDto(        LocalTime.of(14, 0), LocalTime.of(12, 0)),
        new AddressDto(9, "Pobediteley Avenue", DEFAULT_CITY, DEFAULT_COUNTRY, "220004"),
        List.of("Pool")
    );
  }

  public static HotelShortDto createDefaultHotelShortDto(Long id) {
    return new HotelShortDto(
        id,
        DEFAULT_NAME,
        "Test description",
        "9 Pobediteley Avenue, Minsk, Belarus, 220004",
        DEFAULT_PHONE
    );
  }

  public static List<String> amenityNames() {
    return List.of("Free parking",
        "Free WiFi",
        "Non-smoking rooms",
        "Concierge",
        "On-site restaurant",
        "Fitness center",
        "Pet-friendly rooms",
        "Room service",
        "Business center",
        "Meeting rooms"
    );
  }

  public static Amenity amenityWithName(String name) {
    Amenity amenity = new Amenity();
    amenity.setName(name);
    return amenity;
  }

}

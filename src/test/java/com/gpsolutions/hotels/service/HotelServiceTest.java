package com.gpsolutions.hotels.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.gpsolutions.hotels.HotelTestData;
import com.gpsolutions.hotels.domain.dto.request.HotelCreateDto;
import com.gpsolutions.hotels.domain.dto.request.HotelSpecsDto;
import com.gpsolutions.hotels.domain.entity.Amenity;
import com.gpsolutions.hotels.domain.entity.Hotel;
import com.gpsolutions.hotels.domain.mapper.AmenityRepository;
import com.gpsolutions.hotels.domain.mapper.HotelMapper;
import com.gpsolutions.hotels.exception.ResourceNotFoundException;
import com.gpsolutions.hotels.repository.HotelRepository;
import com.gpsolutions.hotels.service.impl.HotelServiceImpl;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

@ExtendWith(MockitoExtension.class)
class HotelServiceTest {

  @Mock
  private HotelRepository hotelRepository;
  @Mock
  private AmenityRepository amenityRepository;
  @Mock
  private HotelMapper hotelMapper;

  @InjectMocks
  private HotelServiceImpl hotelService;

  @Test
  void findById_hotelExists_returnDetailedDto() {
    // given
    var id = 1L;
    var hotel = HotelTestData.createDefaultHotel();
    hotel.setId(id);
    var expected = HotelTestData.createDefaultHotelDto(id);

    when(hotelRepository.findById(id)).thenReturn(Optional.of(hotel));
    when(hotelMapper.toDto(hotel)).thenReturn(expected);

    // when
    var result = hotelService.findById(id);

    // then
    assertThat(result).isEqualTo(expected);
    verify(hotelRepository).findById(id);
    verify(hotelMapper).toDto(hotel);
  }

  @Test
  void findById_hotelDoesNotExist_throwResourceNotFound() {
    // given
    var id = Long.MAX_VALUE;
    when(hotelRepository.findById(id)).thenReturn(Optional.empty());

    // when / then
    assertThatExceptionOfType(ResourceNotFoundException.class)
        .isThrownBy(() -> hotelService.findById(id));
  }

  @Test
  void findAll_whenNoHotels_returnEmptyList() {
    // given
    when(hotelRepository.findAll()).thenReturn(List.of());

    // when
    var result = hotelService.findAll();

    // then
    assertThat(result).isEmpty();
  }

  @Test
  void findAll_hotelsExist_returnMappedList() {
    // given
    var first = HotelTestData.createDefaultHotel();
    first.setId(1L);
    var second = HotelTestData.createDefaultHotel();
    second.setId(2L);
    second.setName("Hotel 2");

    var firstShort = HotelTestData.createDefaultHotelShortDto(1L);
    var secondShort = HotelTestData.createDefaultHotelShortDto(2L);

    // given
    when(hotelRepository.findAll()).thenReturn(List.of(first, second));
    when(hotelMapper.toShortDto(first)).thenReturn(firstShort);
    when(hotelMapper.toShortDto(second)).thenReturn(secondShort);

    // when
    var result = hotelService.findAll();

    // then
    assertThat(result).containsExactly(firstShort, secondShort);
  }

  @Test
  @SuppressWarnings("unchecked")
  void findAllBySpecs_matchExists_returnMappedList() {
    // given
    var specs = new HotelSpecsDto(HotelTestData.DEFAULT_NAME, HotelTestData.DEFAULT_BRAND, null,
        null, null);
    var hotel = HotelTestData.createDefaultHotel();
    hotel.setId(1L);
    var shortDto = HotelTestData.createDefaultHotelShortDto(1L);

    when(hotelRepository.findAll(any(Specification.class))).thenReturn(List.of(hotel));
    when(hotelMapper.toShortDto(hotel)).thenReturn(shortDto);

    // when
    var result = hotelService.findAll(specs);

    // then
    assertThat(result).containsExactly(shortDto);
    verify(hotelRepository).findAll(any(Specification.class));
  }

  @Test
  void save_validCreateDto_returnShortDto() {
    // given
    HotelCreateDto createDto = HotelTestData.createDefaultHotelCreateDto();
    var mapped = HotelTestData.createDefaultHotel();
    var saved = HotelTestData.createDefaultHotel();
    saved.setId(1L);
    var expected = HotelTestData.createDefaultHotelShortDto(1L);

    when(hotelMapper.toEntity(createDto)).thenReturn(mapped);
    when(hotelRepository.save(mapped)).thenReturn(saved);
    when(hotelMapper.toShortDto(saved)).thenReturn(expected);

    // when
    var result = hotelService.save(createDto);

    // then
    assertThat(result).isEqualTo(expected);
  }

  @Test
  void addAmenities_hotelDoesNotExist_throwResourceNotFound() {
    // given
    var id = Long.MAX_VALUE;
    when(hotelRepository.existsById(id)).thenReturn(false);

    // when / then
    assertThatExceptionOfType(ResourceNotFoundException.class)
        .isThrownBy(() -> hotelService.addAmenities(id, HotelTestData.amenityNames()));
    // then
    verify(amenityRepository, never()).saveAll(any());
  }

  @Test
  @SuppressWarnings("unchecked")
  void addAmenities_hotelExists_saveAmenitiesWithReference() {
    // given
    var id = 1L;
    var hotelProxy = new Hotel();
    hotelProxy.setId(id);
    var names = HotelTestData.amenityNames();

    when(hotelRepository.existsById(id)).thenReturn(true);
    when(hotelRepository.getReferenceById(id)).thenReturn(hotelProxy);

    // when
    hotelService.addAmenities(id, names);

    // then
    ArgumentCaptor<List<Amenity>> captor = ArgumentCaptor.forClass(List.class);
    verify(amenityRepository).saveAll(captor.capture());

    var savedAmenities = captor.getValue();
    assertThat(savedAmenities).hasSize(names.size());
    assertThat(savedAmenities).extracting(Amenity::getName).containsExactlyElementsOf(names);
    assertThat(savedAmenities).allMatch(amenity -> amenity.getHotel() == hotelProxy);
  }

  @Test
  @SuppressWarnings("unchecked")
  void addAmenities_emptyList_passEmptyToRepository() {
    // given
    var id = 1L;
    var hotelProxy = new Hotel();
    hotelProxy.setId(id);

    when(hotelRepository.existsById(id)).thenReturn(true);
    when(hotelRepository.getReferenceById(id)).thenReturn(hotelProxy);

    // when
    hotelService.addAmenities(id, List.of());

    // then
    ArgumentCaptor<List<Amenity>> captor = ArgumentCaptor.forClass(List.class);
    verify(amenityRepository).saveAll(captor.capture());
    assertThat(captor.getValue()).isEmpty();
  }
}

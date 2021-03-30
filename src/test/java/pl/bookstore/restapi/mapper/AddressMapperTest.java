package pl.bookstore.restapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import pl.bookstore.restapi.model.AddressEntity;
import pl.bookstore.restapi.model.UserEntity;
import pl.bookstore.restapi.model.dto.AddressAddDto;
import pl.bookstore.restapi.model.dto.AddressDto;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class AddressMapperTest {

    private final String addressLine1 = "addressLine1";
    private final String addressLine2 = "addressLine2";
    private final String addressLine3 = "addressLine3";
    private final String city = "city";
    private final String state = "state";
    private final String postalCode = "postalCode";
    private final String country = "country";

    @Spy
    @InjectMocks
    AddressMapper addressMapper;

    @Test
    void whenCallToDtosThenReturnListOfAddressesDtos() {
        AddressDto addressDto = mock(AddressDto.class);
        AddressEntity addressEntity = mock(AddressEntity.class);

        doReturn(addressDto).when(addressMapper).toDto(addressEntity);

        List<AddressDto> result = addressMapper.toDtos(List.of(addressEntity));
        assertEquals(1, result.size());
        assertEquals(addressDto, result.get(0));
    }

    @Test
    void whenCallToDtoThenReturnAddressDto() {
        AddressEntity addressEntity = mock(AddressEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        long addressId = 1L;
        String login = "login";

        when(addressEntity.getAddressId()).thenReturn(addressId);
        when(addressEntity.getAddressLine1()).thenReturn(addressLine1);
        when(addressEntity.getAddressLine2()).thenReturn(addressLine2);
        when(addressEntity.getAddressLine3()).thenReturn(addressLine3);
        when(addressEntity.getCity()).thenReturn(city);
        when(addressEntity.getState()).thenReturn(state);
        when(addressEntity.getPostalCode()).thenReturn(postalCode);
        when(addressEntity.getCountry()).thenReturn(country);
        when(addressEntity.getUserEntity()).thenReturn(userEntity);
        when(userEntity.getLogin()).thenReturn(login);

        AddressDto result = addressMapper.toDto(addressEntity);
        assertEquals(addressId, result.getAddressId());
        assertEquals(addressLine1, result.getAddressLine1());
        assertEquals(addressLine2, result.getAddressLine2());
        assertEquals(addressLine3, result.getAddressLine3());
        assertEquals(city, result.getCity());
        assertEquals(state, result.getState());
        assertEquals(postalCode, result.getPostalCode());
        assertEquals(country, result.getCountry());
        assertEquals(login, result.getLogin());
    }

    @Test
    void whenCallToEntityThenReturnAddressEntity() {
        AddressAddDto addressDto = mock(AddressAddDto.class);

        when(addressDto.getAddressLine1()).thenReturn(addressLine1);
        when(addressDto.getAddressLine2()).thenReturn(addressLine2);
        when(addressDto.getAddressLine3()).thenReturn(addressLine3);
        when(addressDto.getCity()).thenReturn(city);
        when(addressDto.getState()).thenReturn(state);
        when(addressDto.getPostalCode()).thenReturn(postalCode);
        when(addressDto.getCountry()).thenReturn(country);

        AddressEntity result = addressMapper.toEntity(addressDto);
        assertEquals(addressLine1, result.getAddressLine1());
        assertEquals(addressLine2, result.getAddressLine2());
        assertEquals(addressLine3, result.getAddressLine3());
        assertEquals(city, result.getCity());
        assertEquals(state, result.getState());
        assertEquals(postalCode, result.getPostalCode());
        assertEquals(country, result.getCountry());
    }
}
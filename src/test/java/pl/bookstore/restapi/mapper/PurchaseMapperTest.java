package pl.bookstore.restapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;
import pl.bookstore.restapi.model.AddressEntity;
import pl.bookstore.restapi.model.PurchaseEntity;
import pl.bookstore.restapi.model.UserEntity;
import pl.bookstore.restapi.model.dto.PurchaseDto;
import pl.bookstore.restapi.repository.AddressRepository;
import pl.bookstore.restapi.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PurchaseMapperTest {

    private final long addressId = 3L;
    private final String login = "john";
    private final LocalDateTime createdAt = LocalDateTime.now();

    @InjectMocks
    private PurchaseMapper purchaseMapper;

    @Mock
    private UserRepository userRepository;

    @Mock
    private AddressRepository addressRepository;

    @Test
    void whenCallToDtoThenReturnPurchaseDto() {
        PurchaseEntity purchaseEntity = mock(PurchaseEntity.class);
        AddressEntity addressEntity = mock(AddressEntity.class);
        UserEntity userEntity = mock(UserEntity.class);
        long purchaseId = 1L;

        when(purchaseEntity.getPurchaseId()).thenReturn(purchaseId);
        when(purchaseEntity.getCreatedAt()).thenReturn(createdAt);
        when(purchaseEntity.getUserEntity()).thenReturn(userEntity);
        when(purchaseEntity.getAddressEntity()).thenReturn(addressEntity);
        when(userEntity.getLogin()).thenReturn(login);
        when(addressEntity.getAddressId()).thenReturn(addressId);

        PurchaseDto result = purchaseMapper.toDto(purchaseEntity);
        assertEquals(purchaseId, result.getPurchaseId());
        assertEquals(createdAt, result.getCreatedAt());
        assertEquals(login, result.getLogin());
        assertEquals(addressId, result.getAddressId());
    }

    @Test
    void whenCallToEntityThenReturnPurchaseEntity() {
        PurchaseDto purchaseDto = mock(PurchaseDto.class);
        AddressEntity addressEntity = mock(AddressEntity.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(purchaseDto.getLogin()).thenReturn(login);
        when(purchaseDto.getAddressId()).thenReturn(addressId);
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(userEntity));
        when(addressRepository.findById(addressId)).thenReturn(Optional.of(addressEntity));

        PurchaseEntity result = purchaseMapper.toEntity(purchaseDto);
        assertEquals(userEntity, result.getUserEntity());
        assertEquals(addressEntity, result.getAddressEntity());
    }

    @Test
    void whenUserNotFoundThenReturn404() {
        PurchaseDto purchaseDto = mock(PurchaseDto.class);

        when(purchaseDto.getLogin()).thenReturn(login);
        when(userRepository.findByLogin(login)).thenReturn(Optional.empty());

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> purchaseMapper.toEntity(purchaseDto));
        assertEquals("404 NOT_FOUND \"User 'john' not found.\"", exception.getMessage());
    }

    @Test
    void whenAddressNotFoundThenReturn404() {
        PurchaseDto purchaseDto = mock(PurchaseDto.class);
        UserEntity userEntity = mock(UserEntity.class);

        when(purchaseDto.getLogin()).thenReturn(login);
        when(purchaseDto.getAddressId()).thenReturn(addressId);
        when(userRepository.findByLogin(login)).thenReturn(Optional.of(userEntity));
        when(addressRepository.findById(addressId)).thenReturn(Optional.empty());

        ResponseStatusException exception =
                assertThrows(ResponseStatusException.class, () -> purchaseMapper.toEntity(purchaseDto));
        assertEquals("404 NOT_FOUND \"Address with id 3 not found.\"", exception.getMessage());
    }
}
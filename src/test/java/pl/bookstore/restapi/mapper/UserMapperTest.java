package pl.bookstore.restapi.mapper;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;
import pl.bookstore.restapi.model.RoleEntity;
import pl.bookstore.restapi.model.UserEntity;
import pl.bookstore.restapi.model.dto.UserDto;
import pl.bookstore.restapi.repository.RoleRepository;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserMapperTest {

    private final String role = "ADMIN";
    private final LocalDateTime registerAt = LocalDateTime.now();
    private final String email = "email@email.com";
    private final String login = "login";
    private final String firstName = "John";
    private final String lastName = "Smith";
    private final String phone = "9537459843";

    @Spy
    @InjectMocks
    private UserMapper userMapper;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private RoleRepository roleRepository;

    @Test
    void whenCallToDtoThenReturnUserDto() {
        UserEntity userEntity = mock(UserEntity.class);
        RoleEntity roleEntity = mock(RoleEntity.class);
        List<RoleEntity> roleEntities = List.of(roleEntity);

        when(userEntity.getRegisterAt()).thenReturn(registerAt);
        when(userEntity.getEmail()).thenReturn(email);
        when(userEntity.getLogin()).thenReturn(login);
        when(userEntity.getFirstName()).thenReturn(firstName);
        when(userEntity.getLastName()).thenReturn(lastName);
        when(userEntity.getPhone()).thenReturn(phone);
        when(userEntity.getRoleEntities()).thenReturn(roleEntities);
        when(roleEntity.getName()).thenReturn(role);

        UserDto result = userMapper.toDto(userEntity);
        assertEquals(registerAt, result.getRegisterAt());
        assertEquals(email, result.getEmail());
        assertEquals(login, result.getLogin());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(phone, result.getPhone());
        assertEquals(List.of(role), result.getRoles());
    }

    @Test
    void whenCallToEntityThenReturnUserEntity() {
        UserDto userDto = mock(UserDto.class);
        RoleEntity roleEntity = mock(RoleEntity.class);
        List<RoleEntity> roleEntities = List.of(roleEntity);
        String password = "qwerty67";

        when(userDto.getEmail()).thenReturn(email);
        when(userDto.getLogin()).thenReturn(login);
        when(userDto.getPassword()).thenReturn(password);
        when(userDto.getFirstName()).thenReturn(firstName);
        when(userDto.getLastName()).thenReturn(lastName);
        when(userDto.getPhone()).thenReturn(phone);
        when(userDto.getRoles()).thenReturn(List.of(role));
        when(roleRepository.findAllByNameIn(List.of(role))).thenReturn(roleEntities);

        UserEntity result = userMapper.toEntity(userDto);
        assertEquals(email, result.getEmail());
        assertEquals(login, result.getLogin());
        assertEquals(passwordEncoder.encode(password), result.getPassword());
        assertEquals(firstName, result.getFirstName());
        assertEquals(lastName, result.getLastName());
        assertEquals(phone, result.getPhone());
        assertEquals(roleEntities, result.getRoleEntities());
    }
}
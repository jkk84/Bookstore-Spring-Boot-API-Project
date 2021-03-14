package pl.bookstore.restapi.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import pl.bookstore.restapi.model.RoleEntity;
import pl.bookstore.restapi.model.UserEntity;
import pl.bookstore.restapi.model.dto.UserDto;
import pl.bookstore.restapi.repository.RoleRepository;

import java.util.List;
import java.util.stream.Collectors;


@Component
@RequiredArgsConstructor
public class UserMapper {

    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    public UserDto toDto(UserEntity userEntity) {
        return UserDto.builder()
                .registerAt(userEntity.getRegisterAt())
                .email(userEntity.getEmail())
                .login(userEntity.getLogin())
                .firstName(userEntity.getFirstName())
                .lastName(userEntity.getLastName())
                .phone(userEntity.getPhone())
                .roles(userEntity.getRoleEntities().stream()
                        .map(RoleEntity::getName).collect(Collectors.toList()))
                .build();
    }

    public UserEntity toEntity(UserDto userDto) {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail(userDto.getEmail());
        userEntity.setLogin(userDto.getLogin());
        userEntity.setPassword(passwordEncoder.encode(userDto.getPassword()));
        userEntity.setFirstName(userDto.getFirstName());
        userEntity.setLastName(userDto.getLastName());
        userEntity.setPhone(userDto.getPhone());
        List<RoleEntity> roleEntities = roleRepository.findAllByNameIn(userDto.getRoles());
        userEntity.setRoleEntities(roleEntities);
        return userEntity;
    }
}

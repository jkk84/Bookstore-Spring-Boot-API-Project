package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.dto.UserDto;
import pl.bookstore.restapi.model.dto.UserUpdateDto;

import java.util.Optional;


public interface UserService {

    Optional<UserDto> getUser(String login);

    Optional<UserDto> updateUser(UserUpdateDto userDto, String login);

    void deleteUser(String login);
}

package pl.bookstore.restapi.service;

import pl.bookstore.restapi.model.dto.UserDto;
import java.util.Optional;


public interface UserService {

    Optional<UserDto> getUser(String login);

    Optional<UserDto> updateUser(UserDto userDto, String login);

    void deleteUser(String login);
}

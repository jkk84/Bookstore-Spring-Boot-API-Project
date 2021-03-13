package pl.bookstore.restapi.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.bookstore.restapi.commons.exception.UserNotFoundException;
import pl.bookstore.restapi.mapper.UserMapper;
import pl.bookstore.restapi.model.dto.UserDto;
import pl.bookstore.restapi.repository.UserRepository;
import pl.bookstore.restapi.service.UserService;

import java.util.Optional;


@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public Optional<UserDto> getUser(String login) {
        return Optional.of(userMapper.toDto(userRepository.findByLogin(login)
        .orElseThrow(() -> new UserNotFoundException(login))));
    }

    @Override
    public Optional<UserDto> updateUser(UserDto userDto, String login) {
        return Optional.of(userMapper.toDto(userRepository.findByLogin(login)
            .map(user -> {
                user.setEmail(userDto.getEmail());
                user.setFirstName(userDto.getFirstName());
                user.setLastName(userDto.getLastName());
                user.setPhone(userDto.getPhone());
                return userRepository.save(user);
            })
        .orElseThrow(() -> new UserNotFoundException(login))));
    }

    @Override
    public void deleteUser(String login) {
        try {
            userRepository.deleteByLogin(login);
        } catch (Exception e){
            throw new UserNotFoundException(login);
        }
    }
}

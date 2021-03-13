package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.bookstore.restapi.model.dto.UserDto;
import pl.bookstore.restapi.service.UserService;


@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getUser(@RequestParam String login) {
        return userService.getUser(login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser
            (@RequestBody UserDto userDto, @RequestParam String login) {
        return userService.updateUser(userDto, login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public void deleteUser(@RequestParam String login) {
        userService.deleteUser(login);
    }
}

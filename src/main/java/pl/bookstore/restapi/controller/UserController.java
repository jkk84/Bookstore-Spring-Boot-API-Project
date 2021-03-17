package pl.bookstore.restapi.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import pl.bookstore.restapi.commons.IsAuthenticated;
import pl.bookstore.restapi.model.dto.UserDto;
import pl.bookstore.restapi.model.dto.UserUpdateDto;
import pl.bookstore.restapi.service.UserService;

@IsAuthenticated
@RestController
@RequiredArgsConstructor
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<UserDto> getUser(@AuthenticationPrincipal String login) {
        return userService.getUser(login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping
    public ResponseEntity<UserDto> updateUser
            (@RequestBody UserUpdateDto userDto, @AuthenticationPrincipal String login) {
        return userService.updateUser(userDto, login)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping
    public void deleteUser(@AuthenticationPrincipal String login) {
        userService.deleteUser(login);
    }
}

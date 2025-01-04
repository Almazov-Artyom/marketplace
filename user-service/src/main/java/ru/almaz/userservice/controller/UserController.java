package ru.almaz.userservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.almaz.userservice.dto.UserDto;
import ru.almaz.userservice.service.UserService;

@RestController
@RequestMapping("api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;

    @GetMapping("/username")
    public UserDto getUser(@RequestParam String username) {
        return userService.getUserByUsername(username);
    }

    @PostMapping()
    public UserDto createUser(@RequestBody UserDto userDto) {
        return userService.createUser(userDto);
    }

    @PostMapping("/exist")
    public void UserIsExist(@RequestBody UserDto userDto) {
        userService.isUserExist(userDto);
    }

    @GetMapping("/customer")
    public String customer(){
        return "Customer";
    }

    @GetMapping("/seller")
    public String seller(){
        return "Customer";
    }


}

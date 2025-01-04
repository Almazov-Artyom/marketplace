package ru.almaz.authservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import ru.almaz.authservice.dto.UserDto;

@FeignClient(name="user-service")
public interface UserServiceClient {
    @GetMapping("/api/user/username")
    UserDto getUserByUsername(@RequestParam("username") String username);

    @PostMapping("/api/user")
    UserDto createUser(@RequestBody UserDto userDto);

    @PostMapping("api/user/exist")
    void userIsExist(@RequestBody UserDto userDto);

}

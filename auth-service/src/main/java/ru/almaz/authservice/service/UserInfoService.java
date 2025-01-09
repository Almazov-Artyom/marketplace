package ru.almaz.authservice.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import ru.almaz.authservice.dto.UserInfo;
import ru.almaz.authservice.entity.User;
import ru.almaz.authservice.exception.InvalidTokenException;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserInfoService {
    private final JwtService jwtService;
    private final UserService userService;

    public UserInfo isValid(String bearerToken) {
        String token = bearerToken.substring(7);
        String username = jwtService.extractUserName(token);
        User user = userService.getUserByUsername(username);

        if(jwtService.isTokenValid(token, user)) {
            log.info(user.getUsername());
            log.info(user.getRole().toString());

            return new UserInfo(user.getId(), user.getRole().toString());
        }
        else
            throw new InvalidTokenException("Invalid token");

    }
}

package ru.almaz.authservice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;
import ru.almaz.authservice.dto.UserInfo;
import ru.almaz.authservice.service.UserInfoService;

@RestController
@RequiredArgsConstructor
public class UserInfoController {
    private final UserInfoService jwtValidService;

    @PostMapping("/auth/user-info")
    public ResponseEntity<UserInfo> validateToken(@RequestHeader("Authorization") String bearerToken ) {
        return ResponseEntity.ok(jwtValidService.isValid(bearerToken));
    }


}

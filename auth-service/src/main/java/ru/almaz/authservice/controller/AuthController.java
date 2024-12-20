package ru.almaz.authservice.controller;

import ru.almaz.authservice.dto.JwtAuthenticationResponse;
import ru.almaz.authservice.dto.SignInRequest;
import ru.almaz.authservice.dto.SignUpRequest;
import ru.almaz.authservice.dto.SignUpResponse;
import ru.almaz.authservice.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;
    @PostMapping("/sign-up")
    public SignUpResponse signUp(@RequestBody @Valid SignUpRequest signUpRequest) {
        log.info("signUp request: {}", signUpRequest);
        return authService.signUp(signUpRequest);
    }

    @PostMapping("/sign-in")
    public JwtAuthenticationResponse signIn(@RequestBody @Valid SignInRequest signInRequest) {
        return authService.signIn(signInRequest);
    }
}

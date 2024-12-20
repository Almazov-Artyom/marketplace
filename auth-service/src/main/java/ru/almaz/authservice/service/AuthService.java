package ru.almaz.authservice.service;

import ru.almaz.authservice.dto.JwtAuthenticationResponse;
import ru.almaz.authservice.dto.SignInRequest;
import ru.almaz.authservice.dto.SignUpRequest;
import ru.almaz.authservice.dto.SignUpResponse;
import ru.almaz.authservice.entity.User;
import ru.almaz.authservice.exception.UserUnauthenticatedException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserService userService;

    private final JwtService jwtService;

    private final PasswordEncoder passwordEncoder;

    private final AuthenticationManager authenticationManager;

    public SignUpResponse signUp(SignUpRequest signUpRequest) {
        userService.isUserExist(signUpRequest);
        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userService.createUser(signUpRequest);
    }

    public JwtAuthenticationResponse signIn(SignInRequest signInRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
            );
            User user = (User) authentication.getPrincipal();
            String token = jwtService.generateToken(user);
            return new JwtAuthenticationResponse(token);
        }
        catch (AuthenticationException ex){
            throw new UserUnauthenticatedException("User unauthenticated");
        }

    }


}

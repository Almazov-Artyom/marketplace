package ru.almaz.authservice.service;

import feign.FeignException;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;
import ru.almaz.authservice.dto.*;
import ru.almaz.authservice.entity.User;
import ru.almaz.authservice.exception.InvalidRefreshTokenException;
import ru.almaz.authservice.exception.UserAlreadyExistException;
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
        try{
            userService.isUserExist(signUpRequest);
        }
        catch (FeignException.FeignClientException e){
            throw new UserAlreadyExistException("User already exist");
        }


        signUpRequest.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        return userService.createUser(signUpRequest);
    }

    public SingInResponse signIn(SignInRequest signInRequest) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(signInRequest.getUsername(), signInRequest.getPassword())
            );
            User user = (User) authentication.getPrincipal();
            String accessToken = jwtService.generateAccessToken(user);
            String refreshToken = jwtService.generateRefreshToken(user);
            return new SingInResponse(accessToken, refreshToken);
        }
        catch (AuthenticationException ex){
            throw new UserUnauthenticatedException("User unauthenticated");
        }
    }

    public RefreshTokenResponse refreshToken(RefreshTokenRequest refreshTokenRequest) {
        String refreshToken = refreshTokenRequest.refreshToken();
        String userName = jwtService.extractUserName(refreshToken);

        UserDetails userDetails = userService.getUserByUsername(userName);
        String accessToken;
        if(jwtService.isTokenValid(refreshToken, userDetails)) {
            accessToken = jwtService.generateAccessToken(userDetails);
        }
        else{
            throw new InvalidRefreshTokenException("Invalid refresh token");
        }

        return new RefreshTokenResponse(accessToken, refreshToken);
    }

}

package ru.almaz.authservice.service;

import ru.almaz.authservice.client.UserServiceClient;
import ru.almaz.authservice.dto.SignUpRequest;
import ru.almaz.authservice.dto.SignUpResponse;
import ru.almaz.authservice.dto.UserDto;
import ru.almaz.authservice.entity.User;
import ru.almaz.authservice.exception.UserUnauthenticatedException;
import ru.almaz.authservice.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    private final UserServiceClient userServiceClient;


    public void isUserExist(SignUpRequest signUpRequest) {
        userServiceClient.userIsExist(userMapper.signUpUserToUserDto(signUpRequest));
    }

    public SignUpResponse createUser(SignUpRequest signUpRequest) {
        UserDto userDto = userMapper.signUpUserToUserDto(signUpRequest);
        return userMapper.UserDtoToSignUpResponse(userServiceClient.createUser(userDto));
    }


    public User getUserByUsername(String username) {
        return userMapper.UserDtoToUser(userServiceClient.getUserByUsername(username));
    }

    public User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication.isAuthenticated()){
            return getUserByUsername(authentication.getName());
        }
        else
            throw new UserUnauthenticatedException("User unauthenticated");
    }

    public UserDetailsService getUserDetailsService() {
        return this::getUserByUsername;
    }




}

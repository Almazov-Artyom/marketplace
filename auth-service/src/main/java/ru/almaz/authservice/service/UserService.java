package ru.almaz.authservice.service;

import ru.almaz.authservice.dto.SignUpRequest;
import ru.almaz.authservice.dto.SignUpResponse;
import ru.almaz.authservice.entity.User;
import ru.almaz.authservice.exception.UserAlreadyExistException;
import ru.almaz.authservice.exception.UserUnauthenticatedException;
import ru.almaz.authservice.mapper.UserMapper;
import ru.almaz.authservice.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional()
    public void isUserExist(SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            throw new UserAlreadyExistException("Username already exists");
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new UserAlreadyExistException("Email already exists");
        }
    }

    @Transactional
    public SignUpResponse createUser(SignUpRequest signUpRequest) {
        User user = userMapper.toUser(signUpRequest);
        userRepository.saveAndFlush(user);
        return userMapper.toSignUpResponse(user);
    }

    //TODO::сделай дто
    @Transactional
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));
    }

    @Transactional
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

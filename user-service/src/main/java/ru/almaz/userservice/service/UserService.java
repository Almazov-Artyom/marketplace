package ru.almaz.userservice.service;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.almaz.userservice.dto.UserDto;
import ru.almaz.userservice.entity.User;
import ru.almaz.userservice.exception.UserAlreadyExistException;
import ru.almaz.userservice.exception.UserNotFoundException;
import ru.almaz.userservice.mapper.UserMapper;
import ru.almaz.userservice.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final UserMapper userMapper;

    @Transactional()
    public void isUserExist(UserDto userDto) {
        if(userRepository.existsByUsername(userDto.username())) {
            throw new UserAlreadyExistException("Username already exists");
        }

        if(userRepository.existsByEmail(userDto.email())) {
            throw new UserAlreadyExistException("Email already exists");
        }
    }

    @Transactional
    public UserDto createUser(UserDto userDto) {
        User user = userMapper.userDtoToUser(userDto);
        userRepository.saveAndFlush(user);
        return userMapper.userToUserDto(user);
    }

    @Transactional
    public UserDto getUserByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UserNotFoundException("Username not found"));
        return userMapper.userToUserDto(user);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new UserNotFoundException("User not found"));
    }
}

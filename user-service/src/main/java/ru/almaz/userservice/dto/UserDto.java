package ru.almaz.userservice.dto;


import ru.almaz.userservice.enums.Role;

public record UserDto(
        Long id,
        String username,
        String password,
        String email,
        Role role
) {
}

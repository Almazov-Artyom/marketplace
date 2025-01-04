package ru.almaz.authservice.dto;

import ru.almaz.authservice.enums.Role;

public record UserDto(
        Long id,
        String username,
        String password,
        String email,
        Role role
) {
}


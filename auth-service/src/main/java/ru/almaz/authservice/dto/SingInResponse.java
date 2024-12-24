package ru.almaz.authservice.dto;

public record SingInResponse(
        String accessToken,
        String refreshToken
) {}



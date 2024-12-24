package ru.almaz.authservice.dto;

public record RefreshTokenResponse(
        String accessToken,
        String refreshToken
) {
}

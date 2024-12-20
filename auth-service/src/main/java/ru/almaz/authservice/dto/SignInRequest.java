package ru.almaz.authservice.dto;

import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class SignInRequest {

    @NotBlank
    private String username;

    @NotBlank
    private String password;

}

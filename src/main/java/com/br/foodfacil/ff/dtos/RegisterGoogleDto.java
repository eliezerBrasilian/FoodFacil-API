package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterGoogleDto(
        @NotNull String email,
        String password,
        @NotNull UserRole role,
        @NotNull String name,
        @NotNull String profilePicture
        ) { }

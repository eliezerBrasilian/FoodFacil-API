package com.br.foodfacil.dtos;

import com.br.foodfacil.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDto(@NotNull String email,
                             @NotNull String password,
                             @NotNull UserRole role,
                             @NotNull String name,
                             String profilePicture) { }
package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterDto(@NotNull String email,
                          @NotNull String password,
                          @NotNull UserRole role,
                          @NotNull String name,
                          String profilePicture) { }
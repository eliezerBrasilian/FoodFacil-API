package com.br.foodfacil.ff.dtos;

import com.br.foodfacil.ff.enums.UserRole;
import jakarta.validation.constraints.NotNull;

public record RegisterGoogleDto(@NotNull String email, @NotNull UserRole role ) { }

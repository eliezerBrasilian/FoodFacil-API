package com.br.foodfacil.dtos;

import jakarta.validation.constraints.NotNull;

public record ProfilePhotoDto (@NotNull String userUid,@NotNull String newProfilePhoto){ }

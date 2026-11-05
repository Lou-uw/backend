package com.example.backend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class CategoriaRequest(
    @field:NotBlank
    @field:Size(min = 1, max = 50)
    val nombre: String,
)
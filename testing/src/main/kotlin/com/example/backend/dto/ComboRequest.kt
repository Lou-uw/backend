package com.example.backend.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.PositiveOrZero
import jakarta.validation.constraints.Size

data class ComboRequest(
    @field:NotBlank
    @field:Size(min = 1, max = 100)
    val nombre: String,

    @field:Size(max = 255)
    val descripcion: String? = null,

    @field:PositiveOrZero
    val precio: Double,

    @field:Size(max = 255)
    val imagen: String? = null,

    val activo: Boolean = true,
)

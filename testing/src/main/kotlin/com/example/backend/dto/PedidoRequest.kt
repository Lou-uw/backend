package com.example.backend.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class PedidoRequest(
    @field:NotBlank
    @field:Size(max = 100)
    val nombre: String,

    @field:NotBlank
    @field:Size(max = 30)
    val telefono: String,

    @field:NotBlank
    @field:Size(max = 30)
    val tipoEntrega: String,

    @field:Size(max = 300)
    val direccionEntrega: String? = null,

    @field:NotBlank
    @field:Size(max = 30)
    val formaPago: String,

    @field:NotEmpty
    @field:Valid
    val detalles: List<PedidoDetalleRequest>,
)

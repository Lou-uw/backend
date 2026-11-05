package com.example.backend.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.Size

data class PedidoRequest(
    @field:NotBlank
    @field:Size(max = 100)
    val clienteNombre: String,

    @field:NotBlank
    @field:Size(max = 50)
    val clienteTelefono: String,

    @field:NotBlank
    @field:Size(max = 30)
    val formaPago: String,

    @field:Size(max = 300)
    val direccionEntrega: String? = null,

    @field:NotEmpty
    @field:Valid
    val detalles: List<PedidoDetalleRequest>,
)
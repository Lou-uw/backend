package com.example.backend.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class PedidoDetalleRequest(
    @field:NotNull
    val productoId: Int,

    val tamanioId: Int? = null,      // obligatorio solo en pizzas
    val conBorde: Boolean = false,   // solo en pizzas

    @field:NotNull
    @field:Min(1)
    val cantidad: Int,
)
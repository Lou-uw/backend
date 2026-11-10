package com.example.backend.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class PedidoDetalleRequest(
    // Cada ítem es un producto o un combo, nunca los dos.
    val productoId: Int? = null,
    val comboId: Int? = null,

    val tamanioId: Int? = null,      // obligatorio solo en pizzas
    val conBorde: Boolean = false,   // solo en pizzas

    @field:NotNull
    @field:Min(1)
    val cantidad: Int,
)

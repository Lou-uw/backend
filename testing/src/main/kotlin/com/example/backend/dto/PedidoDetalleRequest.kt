package com.example.backend.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class PedidoDetalleRequest(
    @field:NotNull
    val productoId: Int,
    val tamanioId: Int? = null,
    val conBorde: Boolean = false,
    val cantidad: Int
)
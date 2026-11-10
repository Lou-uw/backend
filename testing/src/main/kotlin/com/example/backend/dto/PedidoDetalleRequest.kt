package com.example.backend.dto

import jakarta.validation.constraints.Max
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class PedidoDetalleRequest(
    val productoId: Int? = null,
    val comboId: Int? = null,

    val tamanioId: Int? = null,
    val conBorde: Boolean = false,

    /** Si es true, el ítem es un regalo de promoción y su precio debe ser 0. */
    val esRegalo: Boolean = false,

    @field:NotNull
    @field:Min(1)
    @field:Max(999)
    val cantidad: Int,
)

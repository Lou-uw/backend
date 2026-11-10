package com.example.backend.dto

import java.time.LocalDate

data class PromoRequest(
    val fecha: LocalDate,
    val diaSemana: String,
    val hayPromoActiva: Boolean,
    val tipoPromo: String?,
    val descripcion: String?,
    val imagen: String?,
)

package com.example.backend.dto

import com.example.backend.entity.Tamanio

data class TamanioRequest(
    val nombre: String,
    val porciones: Int? = null,
    val precioClasica: Double,
    val precioEspecial: Double,
    val precioBorde: Double
)
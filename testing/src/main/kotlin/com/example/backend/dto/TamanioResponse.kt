package com.example.backend.dto

import com.example.backend.entity.Tamanio

data class TamanioResponse(
    val id: Int,
    val nombre: String,
    val porciones: Int?,
    val precioClasica: Double,
    val precioEspecial: Double,
    val precioBorde: Double
) {
    companion object {
        fun fromEntity(t: Tamanio): TamanioResponse =
            TamanioResponse(
                id = t.id ?: 0,
                nombre = t.nombre,
                porciones = t.porciones,
                precioClasica = t.precioClasica,
                precioEspecial = t.precioEspecial,
                precioBorde = t.precioBorde
            )
    }
}
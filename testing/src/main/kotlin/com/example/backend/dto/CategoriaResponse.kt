package com.example.backend.dto

import com.example.backend.entity.Categoria

data class CategoriaResponse(
    val id: Int,
    val nombre: String
) {
    companion object {
        fun fromEntity(c: Categoria): CategoriaResponse =
            CategoriaResponse(
                id = c.id ?: 0,
                nombre = c.nombre
            )
    }
}
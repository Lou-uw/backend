package com.example.backend.dto

import com.example.backend.entity.Combo

data class ComboResponse(
    val id: Int,
    val nombre: String,
    val descripcion: String?,
    val precio: Double,
    val imagen: String?,
    val activo: Boolean
) {
    companion object {
        fun fromEntity(c: Combo): ComboResponse =
            ComboResponse(
                id = c.id ?: 0,
                nombre = c.nombre,
                descripcion = c.descripcion,
                precio = c.precio,
                imagen = c.imagen,
                activo = c.activo
            )
    }
}

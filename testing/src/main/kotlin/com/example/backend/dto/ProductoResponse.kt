package com.example.backend.dto

import com.example.backend.entity.Producto

data class ProductoResponse(
    val id: Int,
    val nombre: String,
    val descripcion: String?,
    val categoriaId: Int,
    val categoriaNombre: String?,
    val precio: Double?, // Nullable para las pizzas
    val stock: Int?,
    val activo: Boolean,
) {
    companion object {
        fun fromEntity(p: Producto): ProductoResponse =
            ProductoResponse(
                id = p.id ?: 0,
                nombre = p.nombre,
                descripcion = p.descripcion,
                categoriaId = p.categoria?.id ?: 0,
                categoriaNombre = p.categoria?.nombre,
                precio = p.precio,
                stock = p.stock,
                activo = p.activo,
            )
    }
}
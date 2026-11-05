package com.example.backend.dto

data class ProductoRequest(
    val nombre: String,
    val descripcion: String? = null,
    val precio: Double? = null, // Nullable para las pizzas (su precio sale del tamaño)
    val stock: Int? = null,
    val activo: Boolean = true,
    val categoriaId: Int
)
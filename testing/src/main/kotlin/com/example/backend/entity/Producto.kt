package com.example.backend.entity

import jakarta.persistence.*

@Entity
@Table(name = "productos")
open class Producto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false, length = 100)
    var nombre: String = "",

    @Column(columnDefinition = "TEXT")
    var descripcion: String? = null,

    @ManyToOne
    @JoinColumn(name = "categoria_id", nullable = false)
    var categoria: Categoria? = null,

    @Column(name = "precio")
    var precio: Double? = null, // Vacío en pizzas (el precio sale del tamaño)

    @Column(name = "stock")
    var stock: Int? = null,

    @Column(name = "activo")
    var activo: Boolean = true
)
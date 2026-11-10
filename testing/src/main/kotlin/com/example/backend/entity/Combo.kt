package com.example.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "combos")
open class Combo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false, length = 100)
    var nombre: String = "",

    @Column(length = 255)
    var descripcion: String? = null,

    @Column(nullable = false)
    var precio: Double = 0.0,

    @Column(length = 255)
    var imagen: String? = null,

    @Column(nullable = false)
    var activo: Boolean = true
)

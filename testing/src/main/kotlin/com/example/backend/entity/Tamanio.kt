package com.example.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "tamanios")
open class Tamanio(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false, length = 30)
    var nombre: String = "",

    @Column(name = "porciones")
    var porciones: Int? = null,

    @Column(name = "precio_clasica", nullable = false)
    var precioClasica: Double = 0.0,

    @Column(name = "precio_especial", nullable = false)
    var precioEspecial: Double = 0.0,

    @Column(name = "precio_borde", nullable = false)
    var precioBorde: Double = 0.0
)
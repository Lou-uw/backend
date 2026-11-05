package com.example.backend.entity

import jakarta.persistence.*

@Entity
@Table(name = "categorias")
open class Categoria(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false, length = 50)
    var nombre: String = "",

    @OneToMany(mappedBy = "categoria", cascade = [CascadeType.ALL])
    var productos: List<Producto> = emptyList()
)
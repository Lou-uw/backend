package com.example.backend.entity

import jakarta.persistence.*
import java.time.LocalDateTime

@Entity
@Table(name = "pedidos")
open class Pedido(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false)
    var fecha: LocalDateTime = LocalDateTime.now(),

    @Column(nullable = false, length = 100)
    var nombre: String = "",

    @Column(nullable = false, length = 30)
    var telefono: String = "",

    @Column(name = "tipo_entrega", nullable = false, length = 30)
    var tipoEntrega: String = "",

    @Column(name = "direccion_entrega", length = 300)
    var direccionEntrega: String? = null,

    @Column(name = "forma_pago", nullable = false, length = 30)
    var formaPago: String = "",

    @Column(nullable = false)
    var total: Double = 0.0,

    @Column(nullable = false, length = 30)
    var estado: String = "PENDIENTE",

    @OneToMany(mappedBy = "pedido", cascade = [CascadeType.ALL], orphanRemoval = true)
    var detalles: MutableList<PedidoDetalle> = mutableListOf()
)
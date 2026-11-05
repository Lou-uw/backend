package com.example.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime

@Entity
@Table(name = "pedidos")
class Pedido(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(name = "fecha")
    var fecha: LocalDateTime? = null,

    @Column(name = "total")
    var total: BigDecimal? = null,

    @Column(name = "estado", length = 50)
    var estado: String? = null,

    @Column(name = "forma_pago", length = 30)
    var formaPago: String? = null,

    @Column(name = "cliente_nombre", length = 100)
    var clienteNombre: String? = null,

    @Column(name = "cliente_telefono", length = 50)
    var clienteTelefono: String? = null,

    @Column(name = "direccion_entrega", length = 300)
    var direccionEntrega: String? = null
)
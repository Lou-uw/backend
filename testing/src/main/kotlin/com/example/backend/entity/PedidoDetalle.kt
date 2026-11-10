package com.example.backend.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "detalle_pedido")
class PedidoDetalle(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pedido_id")
    var pedido: Pedido? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "producto_id")
    var producto: Producto? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "combo_id")
    var combo: Combo? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tamanio_id")
    var tamanio: Tamanio? = null,

    @Column(name = "con_borde")
    var conBorde: Boolean = false,

    @Column(name = "cantidad")
    var cantidad: Int? = null,

    @Column(name = "precio_unitario")
    var precioUnitario: Double? = null,

    @Column(name = "precio_borde")
    var precioBorde: Double = 0.0
)
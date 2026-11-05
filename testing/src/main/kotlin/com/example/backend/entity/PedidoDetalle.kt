package com.example.backend.entity

import jakarta.persistence.*

@Entity
@Table(name = "detalle_pedido")
open class PedidoDetalle(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @ManyToOne
    @JoinColumn(name = "pedido_id", nullable = false)
    var pedido: Pedido? = null,

    @ManyToOne
    @JoinColumn(name = "producto_id", nullable = false)
    var producto: Producto? = null,

    @ManyToOne
    @JoinColumn(name = "tamanio_id")
    var tamanio: Tamanio? = null,

    @Column(name = "con_borde")
    var conBorde: Boolean = false,

    @Column(name = "cantidad")
    var cantidad: Int = 1,

    @Column(name = "precio_unitario")
    var precioUnitario: Double = 0.0,

    @Column(name = "precio_borde")
    var precioBorde: Double = 0.0
)
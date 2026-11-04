package etec.bdd.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.LocalDateTime
import jakarta.persistence.ManyToOne
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn


@Entity
@Table(name = "pedidos")
open class Pedido(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    var fecha: LocalDateTime? = null,

    @Column(precision = 10, scale = 2)
    var total: BigDecimal? = null,

    @Column(length = 100)
    var estado: String? = null,

    @Column(name = "direccion_entrega", length = 300)
    var direccionEntrega: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    var cliente: Cliente? = null
)
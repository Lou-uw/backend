package etec.bdd.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import jakarta.persistence.ManyToOne
import jakarta.persistence.FetchType
import jakarta.persistence.JoinColumn


@Entity
@Table(name = "pedido-detalle")
open class DetallePedido(
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
    @JoinColumn(name = "tamanio_id")
    var tamanio: Tamanio? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borde_id")
    var borde: Borde? = null,

    var cantidad: Int? = null,

    @Column(name = "precio_unitario", precision = 10, scale = 2)
    var precioUnitario: BigDecimal? = null,

    @Column(name = "precio_borde", precision = 10, scale = 2)
    var precioBorde: BigDecimal = BigDecimal.ZERO,
)
package etec.bdd.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "detalle_pedido")
open class DetallePedido(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "pedido_id", nullable = false)
    var pedido: Pedido? = null,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "producto_id", nullable = false)
    var producto: Producto? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tamanio_id")
    var tamanio: Tamanio? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "borde_id")
    var borde: Borde? = null,

    @Column(nullable = false)
    var cantidad: Int = 1,

    @Column(name = "precio_unitario", nullable = false, precision = 10, scale = 2)
    var precioUnitario: BigDecimal = BigDecimal.ZERO,

    @Column(name = "precio_borde", nullable = false, precision = 10, scale = 2)
    var precioBorde: BigDecimal = BigDecimal.ZERO,

    @Column(length = 300)
    var descripcion: String? = null,
)

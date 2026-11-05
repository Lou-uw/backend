package etec.bdd.entity

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

    /**
     * Valores esperados: DELIVERY o RETIRO.
     */
    @Column(name = "tipo_entrega", nullable = false, length = 30)
    var tipoEntrega: String = "",

    @Column(name = "direccion_entrega", length = 300)
    var direccionEntrega: String? = null,

    /**
     * Valores esperados: EFECTIVO, TARJETA o TRANSFERENCIA.
     */
    @Column(name = "forma_pago", nullable = false, length = 30)
    var formaPago: String = "",

    @Column(nullable = false, precision = 10, scale = 2)
    var total: BigDecimal = BigDecimal.ZERO,

    @Column(nullable = false, length = 30)
    var estado: String = "PENDIENTE",
)

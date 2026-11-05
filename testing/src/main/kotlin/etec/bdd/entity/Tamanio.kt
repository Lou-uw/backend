package etec.bdd.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "tamanios")
open class Tamanio(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false, length = 30)
    var nombre: String = "",

    /**
     * Multiplicador que se aplica al precio base del producto.
     * Ej.: 1.00 = tamaño base, 1.25 = 25% más caro.
     */
    @Column(name = "factor_precio", nullable = false, precision = 5, scale = 2)
    var factorPrecio: BigDecimal = BigDecimal.ONE,
)

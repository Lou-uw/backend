package etec.bdd.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "bordes")
open class Borde(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false, length = 50)
    var nombre: String = "",

    /**
     * Precio base del borde. Se multiplica por el factor del tamaño,
     * igual que el precio base del producto.
     */
    @Column(name = "precio_base", nullable = false, precision = 10, scale = 2)
    var precioBase: BigDecimal = BigDecimal.ZERO,
)

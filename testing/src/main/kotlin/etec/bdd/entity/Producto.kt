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
@Table(name = "productos")
open class Producto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(nullable = false, length = 100)
    var nombre: String = "",

    @Column(length = 500)
    var descripcion: String? = null,

    @Column(length = 500)
    var imagen: String? = null,

    @Column(name = "precio_base", nullable = false, precision = 10, scale = 2)
    var precioBase: BigDecimal = BigDecimal.ZERO,

    var stock: Int? = null,

    @Column(nullable = false)
    var activo: Boolean = true,

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "categoria_id", nullable = false)
    var categoria: Categoria? = null,
)

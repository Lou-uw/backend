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
@Table(name = "productos")
open class Producto(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(length = 100)
    var nombre: String? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "categoria_id", nullable = false)
    var categoria: Categoria? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_sabor_id")
    var tipoSabor: TipoSabor? = null,

    @Column(precision = 10, scale = 2)
    var precio: BigDecimal? = null,

    var stock: Int? = null,

    @Column(nullable = false)
    var activo: Boolean = true
)
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
@Table(name = "precios_pizza")
open class PrecioPizza(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tipo_sabor_id", nullable = false)
    var tipoSabor: TipoSabor? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tamanio_id", nullable = false)
    var tamanio: Tamanio? = null,

    @Column(nullable = false, precision = 10, scale = 2)
    var precio: BigDecimal = BigDecimal.ZERO
)
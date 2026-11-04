package etec.bdd.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal

@Entity
@Table(name = "clientes")
open class Cliente(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    var id: Int? = null,

    @Column(length = 100)
    var nombre: String? = null,

    @Column(length = 100)
    var apellido: String? = null,

    @Column(length = 300)
    var direccion: String? = null
)
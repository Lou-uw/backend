package etec.bdd.dto

import etec.bdd.entity.Borde
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer
import java.math.BigDecimal

data class BordeResponse(
    val id: Int,
    val nombre: String,

    @JsonSerialize(using = ToStringSerializer::class)
    val precioBase: BigDecimal,
) {
    companion object {
        fun fromEntity(b: Borde): BordeResponse =
            BordeResponse(
                id = b.id ?: 0,
                nombre = b.nombre,
                precioBase = b.precioBase,
            )
    }
}

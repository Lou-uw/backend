package etec.bdd.dto

import etec.bdd.entity.PrecioBorde
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer
import java.math.BigDecimal

data class PrecioBordeResponse(
    val id: Int,
    val bordeId: Int,
    val tamanioId: Int,

    @JsonSerialize(using = ToStringSerializer::class)
    val precio: BigDecimal,
) {
    companion object {
        fun fromEntity(p: PrecioBorde): PrecioBordeResponse =
            PrecioBordeResponse(
                id = p.id ?: 0,
                bordeId = p.borde?.id ?: 0,
                tamanioId = p.tamanio?.id ?: 0,
                precio = p.precio,
            )
    }
}
package etec.bdd.dto

import etec.bdd.entity.PrecioPizza
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer
import java.math.BigDecimal

data class PrecioPizzaResponse(
    val id: Int,
    val tipoSaborId: Int,
    val tamanioId: Int,

    @JsonSerialize(using = ToStringSerializer::class)
    val precio: BigDecimal,
) {
    companion object {
        fun fromEntity(p: PrecioPizza): PrecioPizzaResponse =
            PrecioPizzaResponse(
                id = p.id ?: 0,
                tipoSaborId = p.tipoSabor?.id ?: 0,
                tamanioId = p.tamanio?.id ?: 0,
                precio = p.precio,
            )
    }
}
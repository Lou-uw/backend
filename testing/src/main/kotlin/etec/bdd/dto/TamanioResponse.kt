package etec.bdd.dto

import etec.bdd.entity.Tamanio
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer
import java.math.BigDecimal

data class TamanioResponse(
    val id: Int,
    val nombre: String,

    @JsonSerialize(using = ToStringSerializer::class)
    val factorPrecio: BigDecimal,
) {
    companion object {
        fun fromEntity(t: Tamanio): TamanioResponse =
            TamanioResponse(
                id = t.id ?: 0,
                nombre = t.nombre,
                factorPrecio = t.factorPrecio,
            )
    }
}

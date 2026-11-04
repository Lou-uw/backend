package etec.bdd.dto

import etec.bdd.entity.Producto
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer
import java.math.BigDecimal

data class ProductoResponse(
    val id: Long,
    val nombre: String,

    @JsonSerialize(using = ToStringSerializer::class)
    val precio: BigDecimal,

    val stock: Int,
) {
    companion object {
        fun fromEntity(p: Producto): ProductoResponse =
            ProductoResponse(
                id = p.id ?: 0L,
                nombre = p.nombre,
                precio = p.precio,
                stock = p.stock,
            )
    }
}
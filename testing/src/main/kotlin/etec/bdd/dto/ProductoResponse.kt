package etec.bdd.dto

import etec.bdd.entity.Producto
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer
import java.math.BigDecimal

data class ProductoResponse(
    val id: Int,
    val nombre: String,
    val categoriaId: Int,
    val tipoSaborId: Int?,

    @JsonSerialize(using = ToStringSerializer::class)
    val precio: BigDecimal?,

    val stock: Int?,
    val activo: Boolean,
) {
    companion object {
        fun fromEntity(p: Producto): ProductoResponse =
            ProductoResponse(
                id = p.id ?: 0,
                nombre = p.nombre ?: "",
                categoriaId = p.categoria?.id ?: 0,
                tipoSaborId = p.tipoSabor?.id,
                precio = p.precio,
                stock = p.stock,
                activo = p.activo,
            )
    }
}
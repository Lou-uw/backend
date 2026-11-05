package etec.bdd.dto

import etec.bdd.entity.Producto
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer
import java.math.BigDecimal

data class ProductoResponse(
    val id: Int,
    val nombre: String,
    val descripcion: String?,
    val imagen: String?,
    val categoriaId: Int,

    @JsonSerialize(using = ToStringSerializer::class)
    val precioBase: BigDecimal,

    val stock: Int?,
    val activo: Boolean,
) {
    companion object {
        fun fromEntity(p: Producto): ProductoResponse =
            ProductoResponse(
                id = p.id ?: 0,
                nombre = p.nombre,
                descripcion = p.descripcion,
                imagen = p.imagen,
                categoriaId = p.categoria?.id ?: 0,
                precioBase = p.precioBase,
                stock = p.stock,
                activo = p.activo,
            )
    }
}

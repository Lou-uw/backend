package etec.bdd.dto

import etec.bdd.entity.DetallePedido
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer
import java.math.BigDecimal

data class DetallePedidoResponse(
    val id: Int,
    val pedidoId: Int?,
    val productoId: Int?,
    val tamanioId: Int?,
    val bordeId: Int?,
    val cantidad: Int?,

    @JsonSerialize(using = ToStringSerializer::class)
    val precioUnitario: BigDecimal?,

    @JsonSerialize(using = ToStringSerializer::class)
    val precioBorde: BigDecimal,

) {
    companion object {
        fun fromEntity(d: DetallePedido): DetallePedidoResponse =
            DetallePedidoResponse(
                id = d.id ?: 0,
                pedidoId = d.pedido?.id,
                productoId = d.producto?.id,
                tamanioId = d.tamanio?.id,
                bordeId = d.borde?.id,
                cantidad = d.cantidad,
                precioUnitario = d.precioUnitario,
                precioBorde = d.precioBorde,
            )
    }
}
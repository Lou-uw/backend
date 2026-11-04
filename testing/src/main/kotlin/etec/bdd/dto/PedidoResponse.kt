package etec.bdd.dto

import etec.bdd.entity.Pedido
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer
import java.math.BigDecimal
import java.time.LocalDateTime

data class PedidoResponse(
    val id: Int,
    val fecha: LocalDateTime?,

    @JsonSerialize(using = ToStringSerializer::class)
    val total: BigDecimal?,

    val estado: String?,
    val direccionEntrega: String?,
    val clienteId: Int?,
) {
    companion object {
        fun fromEntity(p: Pedido): PedidoResponse =
            PedidoResponse(
                id = p.id ?: 0,
                fecha = p.fecha,
                total = p.total,
                estado = p.estado,
                direccionEntrega = p.direccionEntrega,
                clienteId = p.cliente?.id,
            )
    }
}
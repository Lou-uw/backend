package etec.bdd.dto

import etec.bdd.entity.DetallePedido
import etec.bdd.entity.Pedido
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer
import java.math.BigDecimal
import java.time.LocalDateTime

data class PedidoResponse(
    val id: Int,
    val fecha: LocalDateTime,
    val nombre: String,
    val telefono: String,
    val tipoEntrega: String,
    val direccionEntrega: String?,
    val formaPago: String,
    val estado: String,

    @JsonSerialize(using = ToStringSerializer::class)
    val total: BigDecimal,

    val detalles: List<DetallePedidoResponse> = emptyList(),
) {
    companion object {
        fun fromEntity(
            p: Pedido,
            detalles: List<DetallePedido> = emptyList(),
        ): PedidoResponse =
            PedidoResponse(
                id = p.id ?: 0,
                fecha = p.fecha,
                nombre = p.nombre,
                telefono = p.telefono,
                tipoEntrega = p.tipoEntrega,
                direccionEntrega = p.direccionEntrega,
                formaPago = p.formaPago,
                estado = p.estado,
                total = p.total,
                detalles = detalles.map(DetallePedidoResponse::fromEntity),
            )
    }
}

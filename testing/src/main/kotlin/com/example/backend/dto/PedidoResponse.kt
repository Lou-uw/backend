package com.example.backend.dto

import com.example.backend.entity.Pedido
import com.example.backend.entity.PedidoDetalle
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer
import java.time.LocalDateTime

data class PedidoResponse(
    val id: Int,
    val fecha: LocalDateTime?,

    @JsonSerialize(using = ToStringSerializer::class)
    val total: Double?,

    val estado: String?,
    val formaPago: String?,
    val clienteNombre: String?,
    val clienteTelefono: String?,
    val direccionEntrega: String?,
    val notas: String?,
    val detalles: List<PedidoDetalleResponse> = emptyList(),
) {
    companion object {
        fun fromEntity(p: Pedido, detalles: List<PedidoDetalle> = emptyList()): PedidoResponse =
            PedidoResponse(
                id = p.id ?: 0,
                fecha = p.fecha,
                total = p.total,
                estado = p.estado,
                formaPago = p.formaPago,
                clienteNombre = p.clienteNombre,
                clienteTelefono = p.clienteTelefono,
                direccionEntrega = p.direccionEntrega,
                notas = p.notas,
                detalles = detalles.map { PedidoDetalleResponse.fromEntity(it) },
            )
    }
}
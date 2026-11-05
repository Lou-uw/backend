package com.example.backend.dto

import com.example.backend.entity.PedidoDetalle
import com.example.backend.entity.Pedido
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
    val total: Double,
    val detalles: List<PedidoDetalleResponse> = emptyList(),
) {
    companion object {
        fun fromEntity(
            p: Pedido,
            detalles: List<PedidoDetalle> = emptyList(),
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
                detalles = detalles.map(PedidoDetalleResponse::fromEntity),
            )
    }
}
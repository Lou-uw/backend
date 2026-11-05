package com.example.backend.dto

import com.example.backend.entity.PedidoDetalle

data class PedidoDetalleResponse(
    val id: Int,
    val pedidoId: Int?,
    val productoId: Int?,
    val tamanioId: Int?,
    val productoNombre: String?,
    val tamanioNombre: String?,
    val conBorde: Boolean,
    val cantidad: Int,
    val precioUnitario: Double,
    val precioBorde: Double,
) {
    companion object {
        fun fromEntity(d: PedidoDetalle): PedidoDetalleResponse =
            PedidoDetalleResponse(
                id = d.id ?: 0,
                pedidoId = d.pedido?.id,
                productoId = d.producto?.id,
                tamanioId = d.tamanio?.id,
                productoNombre = d.producto?.nombre,
                tamanioNombre = d.tamanio?.nombre,
                conBorde = d.conBorde,
                cantidad = d.cantidad,
                precioUnitario = d.precioUnitario,
                precioBorde = d.precioBorde,
            )
    }
}
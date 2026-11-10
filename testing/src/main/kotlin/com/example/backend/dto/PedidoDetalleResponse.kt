package com.example.backend.dto

import com.example.backend.entity.PedidoDetalle
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer

data class PedidoDetalleResponse(
    val id: Int,
    val productoId: Int?,
    val producto: String,
    val comboId: Int?,
    val combo: String?,
    val tamanioId: Int?,
    val conBorde: Boolean,
    val cantidad: Int,
    val esRegalo: Boolean,

    @JsonSerialize(using = ToStringSerializer::class)
    val precioUnitario: Double,

    @JsonSerialize(using = ToStringSerializer::class)
    val precioBorde: Double,

    @JsonSerialize(using = ToStringSerializer::class)
    val subtotal: Double,
) {
    companion object {
        fun fromEntity(d: PedidoDetalle): PedidoDetalleResponse {
            val unitario = d.precioUnitario ?: 0.0
            val cantidad = d.cantidad ?: 0
            return PedidoDetalleResponse(
                id = d.id ?: 0,
                productoId = d.producto?.id,
                producto = d.producto?.nombre ?: "",
                comboId = d.combo?.id,
                combo = d.combo?.nombre,
                tamanioId = d.tamanio?.id,
                conBorde = d.conBorde,
                cantidad = cantidad,
                esRegalo = d.esRegalo,
                precioUnitario = unitario,
                precioBorde = d.precioBorde,
                subtotal = (unitario + d.precioBorde) * cantidad,
            )
        }
    }
}

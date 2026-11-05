package com.example.backend.dto

import com.example.backend.entity.PedidoDetalle
import tools.jackson.databind.annotation.JsonSerialize
import tools.jackson.databind.ser.std.ToStringSerializer
import java.math.BigDecimal

data class PedidoDetalleResponse(
    val id: Int,
    val productoId: Int?,
    val producto: String,
    val tamanioId: Int?,
    val conBorde: Boolean,
    val cantidad: Int,

    @JsonSerialize(using = ToStringSerializer::class)
    val precioUnitario: BigDecimal,

    @JsonSerialize(using = ToStringSerializer::class)
    val precioBorde: BigDecimal,

    @JsonSerialize(using = ToStringSerializer::class)
    val subtotal: BigDecimal,
) {
    companion object {
        fun fromEntity(d: PedidoDetalle): PedidoDetalleResponse {
            val unitario = d.precioUnitario ?: BigDecimal.ZERO
            val cantidad = d.cantidad ?: 0
            return PedidoDetalleResponse(
                id = d.id ?: 0,
                productoId = d.producto?.id,
                producto = d.producto?.nombre ?: "",
                tamanioId = d.tamanio?.id,
                conBorde = d.conBorde,
                cantidad = cantidad,
                precioUnitario = unitario,
                precioBorde = d.precioBorde,
                subtotal = unitario.add(d.precioBorde).multiply(BigDecimal(cantidad)),
            )
        }
    }
}
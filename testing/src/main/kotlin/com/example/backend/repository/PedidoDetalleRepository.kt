package com.example.backend.repository

import com.example.backend.entity.PedidoDetalle
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoDetalleRepository : JpaRepository<PedidoDetalle, Int> {
    fun findByPedidoId(pedidoId: Int): List<PedidoDetalle>
}
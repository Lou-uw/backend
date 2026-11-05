package etec.bdd.repository

import etec.bdd.entity.DetallePedido
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PedidoDetalleRepository : JpaRepository<DetallePedido, Int> {
    fun findByPedidoId(pedidoId: Int): List<DetallePedido>
}

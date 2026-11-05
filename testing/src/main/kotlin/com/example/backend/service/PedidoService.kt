package com.example.backend.service

import com.example.backend.dto.PedidoRequest
import com.example.backend.dto.PedidoResponse
import com.example.backend.entity.Pedido
import com.example.backend.entity.PedidoDetalle
import com.example.backend.entity.Tamanio
import com.example.backend.exception.ProductoNoEncontradoException
import com.example.backend.exception.RecursoNoEncontradoException
import com.example.backend.exception.SolicitudInvalidaException
import com.example.backend.repository.PedidoDetalleRepository
import com.example.backend.repository.PedidoRepository
import com.example.backend.repository.ProductoRepository
import com.example.backend.repository.TamanioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class PedidoService(
    private val pedidoRepository: PedidoRepository,
    private val detalleRepository: PedidoDetalleRepository,
    private val productoRepository: ProductoRepository,
    private val tamanioRepository: TamanioRepository
) {

    @Transactional(readOnly = true)
    fun listar(): List<PedidoResponse> {
        return pedidoRepository.findAll()
            .map { PedidoResponse.fromEntity(it) }
    }

    @Transactional(readOnly = true)
    fun obtener(id: Int): PedidoResponse {
        val pedido = pedidoRepository.findById(id)
            .orElseThrow { RecursoNoEncontradoException("Pedido", id) }
        val detalles = detalleRepository.findByPedidoId(id)
        return PedidoResponse.fromEntity(pedido, detalles)
    }

    @Transactional
    fun crear(req: PedidoRequest): PedidoResponse {
        val pedido = pedidoRepository.save(
            Pedido(
                fecha = LocalDateTime.now(),
                total = BigDecimal.ZERO,
                estado = "Pendiente",
                formaPago = req.formaPago,
                clienteNombre = req.clienteNombre,
                clienteTelefono = req.clienteTelefono,
                direccionEntrega = req.direccionEntrega
            )
        )

        var total = BigDecimal.ZERO
        val guardados = mutableListOf<PedidoDetalle>()

        for (item in req.detalles) {
            val producto = productoRepository.findById(item.productoId)
                .orElseThrow { ProductoNoEncontradoException(item.productoId) }

            if (!producto.activo) {
                throw SolicitudInvalidaException("El producto '${producto.nombre}' no está disponible")
            }

            val categoriaNombre = producto.categoria?.nombre
            val esPizza = categoriaNombre == "Pizzas Clásicas" || categoriaNombre == "Pizzas Especiales"

            val tamanio: Tamanio?
            val precioUnitario: BigDecimal
            var precioBorde = BigDecimal.ZERO

            if (esPizza) {
                val tamanioId = item.tamanioId
                    ?: throw SolicitudInvalidaException("La pizza '${producto.nombre}' necesita un tamaño")
                val t = tamanioRepository.findById(tamanioId)
                    .orElseThrow { RecursoNoEncontradoException("Tamaño", tamanioId) }
                tamanio = t

                precioUnitario = if (categoriaNombre == "Pizzas Especiales") {
                    BigDecimal.valueOf(t.precioEspecial)
                } else {
                    BigDecimal.valueOf(t.precioClasica)
                }
                if (item.conBorde) {
                    precioBorde = BigDecimal.valueOf(t.precioBorde)
                }
            } else {
                if (item.conBorde) {
                    throw SolicitudInvalidaException("El borde solo se puede pedir con una pizza")
                }
                tamanio = null
                precioUnitario = producto.precio?.let { BigDecimal.valueOf(it) }
                    ?: throw SolicitudInvalidaException("El producto '${producto.nombre}' no tiene precio")
            }

            total = total.add(
                precioUnitario.add(precioBorde).multiply(BigDecimal(item.cantidad))
            )

            guardados.add(
                detalleRepository.save(
                    PedidoDetalle(
                        pedido = pedido,
                        producto = producto,
                        tamanio = tamanio,
                        conBorde = item.conBorde,
                        cantidad = item.cantidad,
                        precioUnitario = precioUnitario,
                        precioBorde = precioBorde
                    )
                )
            )
        }

        pedido.total = total
        pedidoRepository.save(pedido)

        return PedidoResponse.fromEntity(pedido, guardados)
    }
}
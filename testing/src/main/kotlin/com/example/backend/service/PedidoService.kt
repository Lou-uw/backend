package com.example.backend.service

import com.example.backend.dto.PedidoRequest
import com.example.backend.dto.PedidoResponse
import com.example.backend.entity.PedidoDetalle
import com.example.backend.entity.Pedido
import com.example.backend.exception.ProductoNoEncontradoException
import com.example.backend.exception.RecursoNoEncontradoException
import com.example.backend.repository.PedidoDetalleRepository
import com.example.backend.repository.PedidoRepository
import com.example.backend.repository.ProductoRepository
import com.example.backend.repository.TamanioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime

@Service
class PedidoService(
    private val pedidoRepository: PedidoRepository,
    private val detalleRepository: PedidoDetalleRepository,
    private val productoRepository: ProductoRepository,
    private val tamanioRepository: TamanioRepository
) {
    private val tiposEntregaValidos = setOf("DELIVERY", "RETIRO")
    private val formasPagoValidas = setOf("EFECTIVO", "TARJETA", "TRANSFERENCIA")

    fun listar(): List<PedidoResponse> =
        pedidoRepository.findAllByOrderByFechaDesc().map { PedidoResponse.fromEntity(it) }

    @Transactional(readOnly = true)
    fun obtener(id: Int): PedidoResponse {
        val pedido = buscar(id)
        val detalles = detalleRepository.findByPedidoId(id)
        return PedidoResponse.fromEntity(pedido, detalles)
    }

    @Transactional
    fun crear(request: PedidoRequest): PedidoResponse {
        val tipoEntrega = request.tipoEntrega.trim().uppercase()
        val formaPago = request.formaPago.trim().uppercase()

        validarCheckout(tipoEntrega, formaPago, request.direccionEntrega)

        val pedido = Pedido(
            fecha = LocalDateTime.now(),
            nombre = request.nombre.trim(),
            telefono = request.telefono.trim(),
            tipoEntrega = tipoEntrega,
            direccionEntrega = request.direccionEntrega?.trim()?.takeIf { it.isNotEmpty() },
            formaPago = formaPago,
            total = 0.0,
            estado = "PENDIENTE"
        )

        val detalles = request.detalles.map { item ->
            val producto = productoRepository.findById(item.productoId)
                .orElseThrow { ProductoNoEncontradoException(item.productoId) }

            if (!producto.activo) {
                throw IllegalArgumentException("El producto '${producto.nombre}' no está disponible")
            }

            var precioUnitario = 0.0
            var precioBorde = 0.0

            val tamanio = item.tamanioId?.let { tamanioId ->
                tamanioRepository.findById(tamanioId)
                    .orElseThrow { RecursoNoEncontradoException("Tamaño", tamanioId) }
            }

            // Cálculo de precios: Pizzas vs Otros
            if (producto.precio == null) {
                val tamanioElegido = tamanio
                    ?: throw IllegalArgumentException("Debes seleccionar un tamaño para la pizza '${producto.nombre}'")

                val esEspecial = producto.categoria?.nombre?.equals("Pizzas Especiales", ignoreCase = true) == true
                precioUnitario = if (esEspecial) tamanioElegido.precioEspecial else tamanioElegido.precioClasica

                if (item.conBorde) {
                    precioBorde = tamanioElegido.precioBorde
                }
            } else {
                precioUnitario = producto.precio!!
            }

            // Descuento de stock si aplica
            producto.stock?.let { stockActual ->
                if (stockActual < item.cantidad) {
                    throw IllegalArgumentException(
                        "Stock insuficiente para '${producto.nombre}': disponible $stockActual"
                    )
                }
                producto.stock = stockActual - item.cantidad
                productoRepository.save(producto)
            }

            PedidoDetalle(
                pedido = pedido,
                producto = producto,
                tamanio = tamanio,
                conBorde = item.conBorde,
                cantidad = item.cantidad,
                precioUnitario = precioUnitario,
                precioBorde = precioBorde
            )
        }

        pedido.total = calcularTotal(detalles)

        val pedidoGuardado = pedidoRepository.save(pedido)
        val detallesGuardados = detalleRepository.saveAll(detalles)
        return PedidoResponse.fromEntity(pedidoGuardado, detallesGuardados.toList())
    }

    private fun validarCheckout(
        tipoEntrega: String,
        formaPago: String,
        direccionEntrega: String?
    ) {
        if (tipoEntrega !in tiposEntregaValidos) {
            throw IllegalArgumentException(
                "Tipo de entrega inválido: '$tipoEntrega'. Valores válidos: $tiposEntregaValidos"
            )
        }

        if (formaPago !in formasPagoValidas) {
            throw IllegalArgumentException(
                "Forma de pago inválida: '$formaPago'. Valores válidos: $formasPagoValidas"
            )
        }

        if (tipoEntrega == "DELIVERY" && direccionEntrega.isNullOrBlank()) {
            throw IllegalArgumentException("La dirección es obligatoria para pedidos con delivery")
        }
    }

    private fun calcularTotal(detalles: List<PedidoDetalle>): Double =
        detalles.sumOf { detalle ->
            (detalle.precioUnitario + detalle.precioBorde) * detalle.cantidad
        }

    private fun buscar(id: Int): Pedido =
        pedidoRepository.findById(id)
            .orElseThrow { RecursoNoEncontradoException("Pedido", id) }
}
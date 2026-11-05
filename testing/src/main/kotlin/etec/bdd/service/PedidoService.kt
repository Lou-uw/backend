package etec.bdd.service

import etec.bdd.dto.DetallePedidoResponse
import etec.bdd.dto.PedidoRequest
import etec.bdd.dto.PedidoResponse
import etec.bdd.entity.DetallePedido
import etec.bdd.entity.Pedido
import etec.bdd.exception.ProductoNoEncontradoException
import etec.bdd.exception.RecursoNoEncontradoException
import etec.bdd.repository.BordeRepository
import etec.bdd.repository.PedidoDetalleRepository
import etec.bdd.repository.PedidoRepository
import etec.bdd.repository.ProductoRepository
import etec.bdd.repository.TamanioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.math.RoundingMode
import java.time.LocalDateTime

@Service
class PedidoService(
    private val pedidoRepository: PedidoRepository,
    private val detalleRepository: PedidoDetalleRepository,
    private val productoRepository: ProductoRepository,
    private val tamanioRepository: TamanioRepository,
    private val bordeRepository: BordeRepository,
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
            estado = "PENDIENTE",
        )

        val detalles = request.detalles.map { item ->
            val producto = productoRepository.findById(item.productoId)
                .orElseThrow { ProductoNoEncontradoException(item.productoId) }

            if (!producto.activo) {
                throw IllegalArgumentException("El producto '${producto.nombre}' no está disponible")
            }

            val tamanio = item.tamanioId?.let { tamanioId ->
                tamanioRepository.findById(tamanioId)
                    .orElseThrow { RecursoNoEncontradoException("Tamaño", tamanioId) }
            }

            val factor = tamanio?.factorPrecio ?: BigDecimal.ONE

            val borde = item.bordeId?.let { bordeId ->
                bordeRepository.findById(bordeId)
                    .orElseThrow { RecursoNoEncontradoException("Borde", bordeId) }
            }

            val precioUnitario = producto.precioBase
                .multiply(factor)
                .setScale(2, RoundingMode.HALF_UP)

            val precioBorde = (borde?.precioBase ?: BigDecimal.ZERO)
                .multiply(factor)
                .setScale(2, RoundingMode.HALF_UP)

            producto.stock?.let { stockActual ->
                if (stockActual < item.cantidad) {
                    throw IllegalArgumentException(
                        "Stock insuficiente para '${producto.nombre}': disponible $stockActual"
                    )
                }
                producto.stock = stockActual - item.cantidad
                productoRepository.save(producto)
            }

            DetallePedido(
                pedido = pedido,
                producto = producto,
                tamanio = tamanio,
                borde = borde,
                cantidad = item.cantidad,
                precioUnitario = precioUnitario,
                precioBorde = precioBorde,
                descripcion = item.descripcion?.trim(),
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
        direccionEntrega: String?,
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

    private fun calcularTotal(detalles: List<DetallePedido>): BigDecimal =
        detalles.fold(BigDecimal.ZERO) { acumulado, detalle ->
            val subtotal = detalle.precioUnitario
                .add(detalle.precioBorde)
                .multiply(BigDecimal.valueOf(detalle.cantidad.toLong()))
            acumulado.add(subtotal)
        }.setScale(2, RoundingMode.HALF_UP)

    private fun buscar(id: Int): Pedido =
        pedidoRepository.findById(id)
            .orElseThrow { RecursoNoEncontradoException("Pedido", id) }
}

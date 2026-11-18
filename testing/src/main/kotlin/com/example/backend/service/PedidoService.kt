package com.example.backend.service

import com.example.backend.config.PromoConfig
import com.example.backend.dto.PedidoRequest
import com.example.backend.dto.PedidoResponse
import com.example.backend.dto.PromoInfo
import com.example.backend.dto.PedidoDetalleRequest
import com.example.backend.entity.Pedido
import com.example.backend.entity.PedidoDetalle
import com.example.backend.entity.Producto
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
import java.time.LocalDate
import java.time.LocalDateTime

private const val PRODUCTO_PAPA_MEDIANA = "Papas fritas mediana"
private const val PRODUCTO_COCACOLA_1L = "Coca-Cola 1 L"
private const val TAMANIO_GRANDE = "Grande"
private const val TAMANIO_FAMILIAR = "Familiar"

@Service
class PedidoService(
    private val pedidoRepository: PedidoRepository,
    private val detalleRepository: PedidoDetalleRepository,
    private val productoRepository: ProductoRepository,
    private val tamanioRepository: TamanioRepository
) {

    @Transactional(readOnly = true)
    fun listar(): List<PedidoResponse> {
        return pedidoRepository.findAllByOrderByFechaDesc()
            .map { PedidoResponse.fromEntity(it) }
    }

    @Transactional(readOnly = true)
    fun obtener(id: Int): PedidoResponse {
        val pedido = pedidoRepository.findById(id)
            .orElseThrow { RecursoNoEncontradoException("Pedido", id) }
        val detalles = detalleRepository.findByPedidoId(id)
        return PedidoResponse.fromEntity(pedido, detalles)
    }

    @Transactional(readOnly = true)
    fun promoHoy(): PromoInfo = promoParaFecha(PromoConfig.hoy())

    @Transactional(readOnly = true)
    fun promoParaFecha(fecha: LocalDate): PromoInfo {
        return PromoInfo(
            fecha = fecha,
            diaSemana = fecha.dayOfWeek.name,
            hayPromoActiva = PromoConfig.hayPromoActiva(fecha),
            tipoPromo = tipoPromo(fecha),
            descripcion = descripcionPromo(fecha),
            imagen = imagenPromo(fecha)
        )
    }

    @Transactional
    fun crear(req: PedidoRequest): PedidoResponse {
        if (req.detalles.any { it.esRegalo }) {
            throw SolicitudInvalidaException(
                "Los ítems regalo no se pueden enviar desde el cliente; " +
                    "los regalos solo los determina la promoción"
            )
        }

        val fecha = PromoConfig.hoy()
        val regalosPromo = determinarRegalosPromo(req, fecha)
        val todosDetalles = req.detalles + regalosPromo

        val pedido = pedidoRepository.save(
            Pedido(
                fecha = LocalDateTime.now(),
                total = 0.0,
                estado = "Pendiente",
                formaPago = req.formaPago,
                clienteNombre = req.clienteNombre,
                clienteTelefono = req.clienteTelefono,
                direccionEntrega = req.direccionEntrega,
                notas = req.notas
            )
        )

        var total = 0.0
        val guardados = mutableListOf<PedidoDetalle>()

        for (item in todosDetalles) {
            if (item.esRegalo) {
                val productoId = item.productoId
                    ?: throw SolicitudInvalidaException("Regalo de promoción sin producto")
                val producto = productoRepository.findById(productoId)
                    .orElseThrow { ProductoNoEncontradoException(productoId) }

                guardados.add(
                    detalleRepository.save(
                        PedidoDetalle(
                            pedido = pedido,
                            producto = producto,
                            tamanio = null,
                            conBorde = false,
                            cantidad = item.cantidad,
                            precioUnitario = 0.0,
                            precioBorde = 0.0,
                            esRegalo = true
                        )
                    )
                )
                continue
            }

            val productoId = item.productoId
                ?: throw SolicitudInvalidaException("Cada ítem necesita un producto")

            val producto = productoRepository.findById(productoId)
                .orElseThrow { ProductoNoEncontradoException(productoId) }

            if (!producto.activo) {
                throw SolicitudInvalidaException("El producto '${producto.nombre}' no está disponible")
            }

            val categoriaNombre = producto.categoria?.nombre
            val esPizza = categoriaNombre == "Pizzas Clásicas" || categoriaNombre == "Pizzas Especiales"

            val tamanio: Tamanio?
            val precioUnitario: Double
            var precioBorde = 0.0

            if (esPizza) {
                val tamanioId = item.tamanioId
                    ?: throw SolicitudInvalidaException("La pizza '${producto.nombre}' necesita un tamaño")
                val t = tamanioRepository.findById(tamanioId)
                    .orElseThrow { RecursoNoEncontradoException("Tamaño", tamanioId) }
                tamanio = t

                precioUnitario = if (categoriaNombre == "Pizzas Especiales") {
                    t.precioEspecial
                } else {
                    t.precioClasica
                }
                if (item.conBorde) {
                    precioBorde = t.precioBorde
                }
            } else {
                if (item.conBorde) {
                    throw SolicitudInvalidaException("El borde solo se puede pedir con una pizza")
                }
                tamanio = null
                precioUnitario = producto.precio
                    ?: throw SolicitudInvalidaException("El producto '${producto.nombre}' no tiene precio")
            }

            total += (precioUnitario + precioBorde) * item.cantidad

            guardados.add(
                detalleRepository.save(
                    PedidoDetalle(
                        pedido = pedido,
                        producto = producto,
                        tamanio = tamanio,
                        conBorde = item.conBorde,
                        cantidad = item.cantidad,
                        precioUnitario = precioUnitario,
                        precioBorde = precioBorde,
                        esRegalo = false
                    )
                )
            )
        }

        pedido.total = total
        pedidoRepository.save(pedido)

        return PedidoResponse.fromEntity(pedido, guardados)
    }

    private fun determinarRegalosPromo(req: PedidoRequest, fecha: LocalDate): List<PedidoDetalleRequest> {
        if (!PromoConfig.hayPromoActiva(fecha)) return emptyList()

        val tamanioGrande = buscarTamanioPorNombre(TAMANIO_GRANDE)
        val tamanioFamiliar = buscarTamanioPorNombre(TAMANIO_FAMILIAR)

        var pizzasGrande = 0
        var pizzasFamiliar = 0

        for (item in req.detalles) {
            if (item.esRegalo || item.productoId == null || item.tamanioId == null) continue
            val productoOpt = productoRepository.findById(item.productoId)
            if (!productoOpt.isPresent) continue
            val producto = productoOpt.get()
            if (!esPizzaCategoria(producto.categoria?.nombre)) continue

            val tamanioOpt = tamanioRepository.findById(item.tamanioId)
            if (!tamanioOpt.isPresent) continue
            val tamanio = tamanioOpt.get()

            when (tamanio.id) {
                tamanioGrande.id -> pizzasGrande++
                tamanioFamiliar.id -> pizzasFamiliar++
            }
        }

        return when {
            PromoConfig.esMiercoles(fecha) && (pizzasGrande >= 1 || pizzasFamiliar >= 1) ->
                listOf(regalo(buscarProductoPorNombre(PRODUCTO_PAPA_MEDIANA), 1))
            PromoConfig.esViernes(fecha) && pizzasFamiliar >= 1 ->
                listOf(regalo(buscarProductoPorNombre(PRODUCTO_COCACOLA_1L), 1))
            PromoConfig.esFinDeSemana(fecha) && pizzasFamiliar >= 2 ->
                listOf(
                    regalo(buscarProductoPorNombre(PRODUCTO_COCACOLA_1L), 1),
                    regalo(buscarProductoPorNombre(PRODUCTO_PAPA_MEDIANA), 1)
                )
            else -> emptyList()
        }
    }

    private fun buscarProductoPorNombre(nombre: String): Producto =
        productoRepository.findByNombre(nombre)
            .orElseThrow {
                SolicitudInvalidaException(
                    "No se encontró el producto '$nombre' requerido por la promoción"
                )
            }

    private fun buscarTamanioPorNombre(nombre: String): Tamanio =
        tamanioRepository.findByNombre(nombre)
            .orElseThrow {
                SolicitudInvalidaException(
                    "No se encontró el tamaño '$nombre' requerido por la promoción"
                )
            }

    private fun regalo(producto: Producto, cantidad: Int) =
        PedidoDetalleRequest(
            productoId = producto.id
                ?: throw SolicitudInvalidaException("El producto '${producto.nombre}' no tiene ID"),
            esRegalo = true,
            cantidad = cantidad
        )

    private fun esPizzaCategoria(categoriaNombre: String?): Boolean =
        categoriaNombre == "Pizzas Clásicas" || categoriaNombre == "Pizzas Especiales"

    private fun tipoPromo(fecha: LocalDate): String? {
        return when {
            PromoConfig.esMiercoles(fecha) -> "miercoles"
            PromoConfig.esViernes(fecha) -> "viernes"
            PromoConfig.esFinDeSemana(fecha) -> "fin_de_semana"
            else -> null
        }
    }

    private fun descripcionPromo(fecha: LocalDate): String? {
        return when {
            PromoConfig.esMiercoles(fecha) ->
                "Miércoles de papa: comprá una pizza Grande o Familiar y llevá una papa frita mediana de regalo."
            PromoConfig.esViernes(fecha) ->
                "Viernes de gaseosa: comprá una pizza Familiar y llevá una Coca-Cola 1 L de regalo."
            PromoConfig.esFinDeSemana(fecha) ->
                "Fin de semana doble: comprá dos pizzas Familiares y llevá una Coca-Cola 1 L y una papa frita mediana de regalo."
            else -> null
        }
    }

    private fun imagenPromo(fecha: LocalDate): String? {
        return when {
            PromoConfig.esMiercoles(fecha) -> "/images/promo-miercoles.png"
            PromoConfig.esViernes(fecha) -> "/images/promo-viernes.png"
            PromoConfig.esFinDeSemana(fecha) -> "/images/promo-sabado-domingo.png"
            else -> null
        }
    }
}

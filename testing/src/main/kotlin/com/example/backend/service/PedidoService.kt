package com.example.backend.service

import com.example.backend.config.PromoConfig
import com.example.backend.dto.PedidoRequest
import com.example.backend.dto.PedidoResponse
import com.example.backend.dto.PromoInfo
import com.example.backend.dto.PedidoDetalleRequest
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
import java.time.LocalDateTime

internal const val PRODUCTO_PAPA_MEDIANA = 23
internal const val PRODUCTO_COCACOLA_1L = 25

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

    @Transactional(readOnly = true)
    fun promoHoy(): PromoInfo {
        return PromoInfo(
            fecha = PromoConfig.fechaHoy,
            diaSemana = PromoConfig.diaSemana.name,
            hayPromoActiva = PromoConfig.hayPromoActiva,
            tipoPromo = tipoPromoHoy(),
            descripcion = descripcionPromoHoy(),
            imagen = imagenPromoHoy()
        )
    }

    @Transactional
    fun crear(req: PedidoRequest): PedidoResponse {
        val regalosPromo = determinarRegalosPromo(req)
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

    private fun determinarRegalosPromo(req: PedidoRequest): List<PedidoDetalleRequest> {
        if (!PromoConfig.hayPromoActiva) return emptyList()

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
                2 -> pizzasGrande++
                3 -> pizzasFamiliar++
            }
        }

        return when {
            PromoConfig.esMiercoles && (pizzasGrande >= 1 || pizzasFamiliar >= 1) ->
                listOf(regalo(PRODUCTO_PAPA_MEDIANA, 1))
            PromoConfig.esViernes && pizzasFamiliar >= 1 ->
                listOf(regalo(PRODUCTO_COCACOLA_1L, 1))
            PromoConfig.esFinDeSemana && pizzasFamiliar >= 2 ->
                listOf(
                    regalo(PRODUCTO_COCACOLA_1L, 1),
                    regalo(PRODUCTO_PAPA_MEDIANA, 1)
                )
            else -> emptyList()
        }
    }

    private fun regalo(productoId: Int, cantidad: Int) =
        PedidoDetalleRequest(
            productoId = productoId,
            esRegalo = true,
            cantidad = cantidad
        )

    private fun esPizzaCategoria(categoriaNombre: String?): Boolean =
        categoriaNombre == "Pizzas Clásicas" || categoriaNombre == "Pizzas Especiales"

    private fun tipoPromoHoy(): String? {
        return when {
            PromoConfig.esMiercoles -> "miercoles"
            PromoConfig.esViernes -> "viernes"
            PromoConfig.esFinDeSemana -> "fin_de_semana"
            else -> null
        }
    }

    private fun descripcionPromoHoy(): String? {
        return when {
            PromoConfig.esMiercoles ->
                "Miércoles de papa: comprá una pizza Grande o Familiar y llevá una papa frita mediana de regalo."
            PromoConfig.esViernes ->
                "Viernes de gaseosa: comprá una pizza Familiar y llevá una Coca-Cola 1 L de regalo."
            PromoConfig.esFinDeSemana ->
                "Fin de semana doble: comprá dos pizzas Familiares y llevá una Coca-Cola 1 L y una papa frita mediana de regalo."
            else -> null
        }
    }

    private fun imagenPromoHoy(): String? {
        return when {
            PromoConfig.esMiercoles -> "/images/promos/promo-miercoles.png"
            PromoConfig.esViernes -> "/images/promos/promo-viernes.png"
            PromoConfig.esFinDeSemana -> "/images/promos/promo-sabado-domingo.png"
            else -> null
        }
    }
}
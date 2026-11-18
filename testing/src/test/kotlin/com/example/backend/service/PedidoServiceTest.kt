package com.example.backend.service

import com.example.backend.dto.PedidoDetalleRequest
import com.example.backend.dto.PedidoRequest
import com.example.backend.exception.SolicitudInvalidaException
import com.example.backend.repository.PedidoDetalleRepository
import com.example.backend.repository.PedidoRepository
import com.example.backend.repository.ProductoRepository
import com.example.backend.repository.TamanioRepository
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class PedidoServiceTest {

    private val pedidoRepository = mockk<PedidoRepository>()
    private val detalleRepository = mockk<PedidoDetalleRepository>()
    private val productoRepository = mockk<ProductoRepository>()
    private val tamanioRepository = mockk<TamanioRepository>()

    private val pedidoService = PedidoService(
        pedidoRepository = pedidoRepository,
        detalleRepository = detalleRepository,
        productoRepository = productoRepository,
        tamanioRepository = tamanioRepository
    )

    @Test
    fun `crear rechaza items con esRegalo enviados por el cliente`() {
        val request = PedidoRequest(
            clienteNombre = "Cliente de prueba",
            formaPago = "Efectivo",
            direccionEntrega = "Calle Falsa 123",
            detalles = listOf(
                PedidoDetalleRequest(
                    productoId = 1,
                    esRegalo = true,
                    cantidad = 1
                )
            )
        )

        val ex = assertFailsWith<SolicitudInvalidaException> {
            pedidoService.crear(request)
        }

        assertTrue(
            ex.message.orEmpty().contains("regalo", ignoreCase = true),
            "El mensaje de error debe explicar que los regalos no vienen del cliente: ${ex.message}"
        )
        verify(exactly = 0) { pedidoRepository.save(any()) }
        verify(exactly = 0) { detalleRepository.save(any()) }
    }
}

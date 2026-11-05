package com.example.backend.service

import com.example.backend.dto.TamanioRequest
import com.example.backend.dto.TamanioResponse
import com.example.backend.entity.Tamanio
import com.example.backend.exception.RecursoNoEncontradoException
import com.example.backend.repository.TamanioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class TamanioService(
    private val tamanioRepository: TamanioRepository,
) {
    fun listar(): List<TamanioResponse> =
        tamanioRepository.findAll().map(TamanioResponse::fromEntity)

    fun obtener(id: Int): TamanioResponse =
        TamanioResponse.fromEntity(buscar(id))

    @Transactional
    fun crear(request: TamanioRequest): TamanioResponse {
        validarPrecios(request.precioClasica, request.precioEspecial, request.precioBorde)

        val tamanio = Tamanio(
            nombre = request.nombre.trim(),
            porciones = request.porciones,
            precioClasica = request.precioClasica,
            precioEspecial = request.precioEspecial,
            precioBorde = request.precioBorde
        )
        return TamanioResponse.fromEntity(tamanioRepository.save(tamanio))
    }

    @Transactional
    fun actualizar(id: Int, request: TamanioRequest): TamanioResponse {
        validarPrecios(request.precioClasica, request.precioEspecial, request.precioBorde)

        val tamanio = buscar(id)
        tamanio.nombre = request.nombre.trim()
        tamanio.porciones = request.porciones
        tamanio.precioClasica = request.precioClasica
        tamanio.precioEspecial = request.precioEspecial
        tamanio.precioBorde = request.precioBorde

        return TamanioResponse.fromEntity(tamanioRepository.save(tamanio))
    }

    @Transactional
    fun eliminar(id: Int) {
        tamanioRepository.delete(buscar(id))
    }

    private fun buscar(id: Int): Tamanio =
        tamanioRepository.findById(id)
            .orElseThrow { RecursoNoEncontradoException("Tamaño", id) }

    private fun validarPrecios(precioClasica: Double, precioEspecial: Double, precioBorde: Double) {
        if (precioClasica < 0 || precioEspecial < 0 || precioBorde < 0) {
            throw IllegalArgumentException("Los precios no pueden ser negativos")
        }
    }
}
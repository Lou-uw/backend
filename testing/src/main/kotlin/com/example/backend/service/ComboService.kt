package com.example.backend.service

import com.example.backend.dto.ComboRequest
import com.example.backend.dto.ComboResponse
import com.example.backend.entity.Combo
import com.example.backend.exception.RecursoNoEncontradoException
import com.example.backend.repository.ComboRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ComboService(
    private val comboRepository: ComboRepository,
) {
    fun listar(): List<ComboResponse> =
        comboRepository.findAll().map(ComboResponse::fromEntity)

    fun obtener(id: Int): ComboResponse =
        ComboResponse.fromEntity(buscar(id))

    @Transactional
    fun crear(request: ComboRequest): ComboResponse {
        val combo = Combo(
            nombre = request.nombre.trim(),
            descripcion = request.descripcion?.trim(),
            precio = request.precio,
            imagen = request.imagen?.trim(),
            activo = request.activo
        )
        return ComboResponse.fromEntity(comboRepository.save(combo))
    }

    @Transactional
    fun actualizar(id: Int, request: ComboRequest): ComboResponse {
        val combo = buscar(id)
        combo.nombre = request.nombre.trim()
        combo.descripcion = request.descripcion?.trim()
        combo.precio = request.precio
        combo.imagen = request.imagen?.trim()
        combo.activo = request.activo
        return ComboResponse.fromEntity(comboRepository.save(combo))
    }

    @Transactional
    fun eliminar(id: Int) {
        comboRepository.delete(buscar(id))
    }

    private fun buscar(id: Int): Combo =
        comboRepository.findById(id)
            .orElseThrow { RecursoNoEncontradoException("Combo", id) }
}

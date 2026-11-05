package com.example.backend.service

import com.example.backend.repository.CategoriaRepository
import com.example.backend.dto.CategoriaRequest
import com.example.backend.dto.CategoriaResponse
import com.example.backend.entity.Categoria
import com.example.backend.exception.RecursoNoEncontradoException
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class CategoriaService(
    private val categoriaRepository: CategoriaRepository,
) {
    fun listar(): List<CategoriaResponse> =
        categoriaRepository.findAll().map(CategoriaResponse::fromEntity)

    fun obtener(id: Int): CategoriaResponse =
        CategoriaResponse.fromEntity(buscar(id))

    @Transactional
    fun crear(request: CategoriaRequest): CategoriaResponse {
        val categoria = Categoria(nombre = request.nombre.trim())
        return CategoriaResponse.fromEntity(categoriaRepository.save(categoria))
    }

    @Transactional
    fun actualizar(id: Int, request: CategoriaRequest): CategoriaResponse {
        val categoria = buscar(id)
        categoria.nombre = request.nombre.trim()
        return CategoriaResponse.fromEntity(categoriaRepository.save(categoria))
    }

    @Transactional
    fun eliminar(id: Int) {
        categoriaRepository.delete(buscar(id))
    }

    private fun buscar(id: Int): Categoria =
        categoriaRepository.findById(id)
            .orElseThrow { RecursoNoEncontradoException("Categoría", id) }
}

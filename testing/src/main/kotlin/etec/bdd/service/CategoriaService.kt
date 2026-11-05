package etec.bdd.service

import etec.bdd.dto.CategoriaRequest
import etec.bdd.dto.CategoriaResponse
import etec.bdd.entity.Categoria
import etec.bdd.exception.RecursoNoEncontradoException
import etec.bdd.repository.CategoriaRepository
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

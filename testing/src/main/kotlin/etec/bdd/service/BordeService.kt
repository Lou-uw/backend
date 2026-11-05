package etec.bdd.service

import etec.bdd.dto.BordeRequest
import etec.bdd.dto.BordeResponse
import etec.bdd.entity.Borde
import etec.bdd.exception.RecursoNoEncontradoException
import etec.bdd.repository.BordeRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

@Service
class BordeService(
    private val bordeRepository: BordeRepository,
) {
    fun listar(): List<BordeResponse> =
        bordeRepository.findAll().map(BordeResponse::fromEntity)

    fun obtener(id: Int): BordeResponse =
        BordeResponse.fromEntity(buscar(id))

    @Transactional
    fun crear(request: BordeRequest): BordeResponse {
        validarPrecio(request.precioBase)
        val borde = Borde(
            nombre = request.nombre.trim(),
            precioBase = request.precioBase,
        )
        return BordeResponse.fromEntity(bordeRepository.save(borde))
    }

    @Transactional
    fun actualizar(id: Int, request: BordeRequest): BordeResponse {
        validarPrecio(request.precioBase)
        val borde = buscar(id)
        borde.nombre = request.nombre.trim()
        borde.precioBase = request.precioBase
        return BordeResponse.fromEntity(bordeRepository.save(borde))
    }

    @Transactional
    fun eliminar(id: Int) {
        bordeRepository.delete(buscar(id))
    }

    private fun buscar(id: Int): Borde =
        bordeRepository.findById(id)
            .orElseThrow { RecursoNoEncontradoException("Borde", id) }

    private fun validarPrecio(precioBase: BigDecimal) {
        if (precioBase.signum() < 0) {
            throw IllegalArgumentException("El precio del borde no puede ser negativo")
        }
    }
}

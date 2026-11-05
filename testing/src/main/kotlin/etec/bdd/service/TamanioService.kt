package etec.bdd.service

import etec.bdd.dto.TamanioRequest
import etec.bdd.dto.TamanioResponse
import etec.bdd.entity.Tamanio
import etec.bdd.exception.RecursoNoEncontradoException
import etec.bdd.repository.TamanioRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal

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
        validarFactor(request.factorPrecio)
        val tamanio = Tamanio(
            nombre = request.nombre.trim(),
            factorPrecio = request.factorPrecio,
        )
        return TamanioResponse.fromEntity(tamanioRepository.save(tamanio))
    }

    @Transactional
    fun actualizar(id: Int, request: TamanioRequest): TamanioResponse {
        validarFactor(request.factorPrecio)
        val tamanio = buscar(id)
        tamanio.nombre = request.nombre.trim()
        tamanio.factorPrecio = request.factorPrecio
        return TamanioResponse.fromEntity(tamanioRepository.save(tamanio))
    }

    @Transactional
    fun eliminar(id: Int) {
        tamanioRepository.delete(buscar(id))
    }

    private fun buscar(id: Int): Tamanio =
        tamanioRepository.findById(id)
            .orElseThrow { RecursoNoEncontradoException("Tamaño", id) }

    private fun validarFactor(factorPrecio: BigDecimal) {
        if (factorPrecio.signum() <= 0) {
            throw IllegalArgumentException("El factor de precio debe ser mayor a cero")
        }
    }
}

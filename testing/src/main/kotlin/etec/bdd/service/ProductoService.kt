package etec.bdd.service

import etec.bdd.dto.ProductoRequest
import etec.bdd.dto.ProductoResponse
import etec.bdd.entity.Categoria
import etec.bdd.entity.Producto
import etec.bdd.exception.ProductoNoEncontradoException
import etec.bdd.exception.RecursoNoEncontradoException
import etec.bdd.repository.CategoriaRepository
import etec.bdd.repository.ProductoRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductoService(
    private val productoRepository: ProductoRepository,
    private val categoriaRepository: CategoriaRepository,
) {
    fun listar(categoriaId: Int? = null): List<ProductoResponse> {
        val productos = if (categoriaId != null) {
            productoRepository.findByCategoriaId(categoriaId)
        } else {
            productoRepository.findAll()
        }
        return productos.map(ProductoResponse::fromEntity)
    }

    fun obtener(id: Int): ProductoResponse =
        ProductoResponse.fromEntity(buscar(id))

    @Transactional
    fun crear(request: ProductoRequest): ProductoResponse {
        val producto = Producto(
            nombre = request.nombre.trim(),
            descripcion = request.descripcion?.trim(),
            imagen = request.imagen?.trim(),
            precioBase = request.precioBase,
            stock = request.stock,
            activo = request.activo,
            categoria = buscarCategoria(request.categoriaId),
        )
        return ProductoResponse.fromEntity(productoRepository.save(producto))
    }

    @Transactional
    fun actualizar(id: Int, request: ProductoRequest): ProductoResponse {
        val producto = buscar(id)
        producto.nombre = request.nombre.trim()
        producto.descripcion = request.descripcion?.trim()
        producto.imagen = request.imagen?.trim()
        producto.precioBase = request.precioBase
        producto.stock = request.stock
        producto.activo = request.activo
        producto.categoria = buscarCategoria(request.categoriaId)
        return ProductoResponse.fromEntity(productoRepository.save(producto))
    }

    /**
     * Baja lógica: conserva el producto para no romper pedidos históricos.
     */
    @Transactional
    fun eliminar(id: Int) {
        val producto = buscar(id)
        producto.activo = false
        productoRepository.save(producto)
    }

    private fun buscar(id: Int): Producto =
        productoRepository.findById(id)
            .orElseThrow { ProductoNoEncontradoException(id) }

    private fun buscarCategoria(id: Int): Categoria =
        categoriaRepository.findById(id)
            .orElseThrow { RecursoNoEncontradoException("Categoría", id) }
}

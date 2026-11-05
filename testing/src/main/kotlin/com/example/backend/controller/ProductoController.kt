package com.example.backend.controller

import com.example.backend.dto.ProductoRequest
import com.example.backend.dto.ProductoResponse
import com.example.backend.service.ProductoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/productos")
class ProductoController(
    private val productoService: ProductoService,
) {
    @GetMapping
    fun listar(
        @RequestParam(required = false) categoriaId: Int?,
    ): List<ProductoResponse> = productoService.listar(categoriaId)

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Int): ProductoResponse = productoService.obtener(id)

    @PostMapping
    fun crear(@Valid @RequestBody request: ProductoRequest): ResponseEntity<ProductoResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(productoService.crear(request))

    @PutMapping("/{id}")
    fun actualizar(
        @PathVariable id: Int,
        @Valid @RequestBody request: ProductoRequest,
    ): ProductoResponse = productoService.actualizar(id, request)

    /**
     * Baja lógica: el producto deja de estar activo pero no se borra.
     */
    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> {
        productoService.eliminar(id)
        return ResponseEntity.noContent().build()
    }
}

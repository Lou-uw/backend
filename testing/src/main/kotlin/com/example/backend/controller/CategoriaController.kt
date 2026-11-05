package com.example.backend.controller

import com.example.backend.dto.CategoriaRequest
import com.example.backend.dto.CategoriaResponse
import com.example.backend.service.CategoriaService
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
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/categorias")
class CategoriaController(
    private val categoriaService: CategoriaService,
) {
    @GetMapping
    fun listar(): List<CategoriaResponse> = categoriaService.listar()

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Int): CategoriaResponse = categoriaService.obtener(id)

    @PostMapping
    fun crear(@Valid @RequestBody request: CategoriaRequest): ResponseEntity<CategoriaResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(categoriaService.crear(request))

    @PutMapping("/{id}")
    fun actualizar(
        @PathVariable id: Int,
        @Valid @RequestBody request: CategoriaRequest,
    ): CategoriaResponse = categoriaService.actualizar(id, request)

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> {
        categoriaService.eliminar(id)
        return ResponseEntity.noContent().build()
    }
}

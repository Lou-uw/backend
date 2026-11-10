package com.example.backend.controller

import com.example.backend.dto.ComboRequest
import com.example.backend.dto.ComboResponse
import com.example.backend.service.ComboService
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
@RequestMapping("/api/combos")
class ComboController(
    private val comboService: ComboService,
) {
    @GetMapping
    fun listar(): List<ComboResponse> = comboService.listar()

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Int): ComboResponse = comboService.obtener(id)

    @PostMapping
    fun crear(@Valid @RequestBody request: ComboRequest): ResponseEntity<ComboResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(comboService.crear(request))

    @PutMapping("/{id}")
    fun actualizar(
        @PathVariable id: Int,
        @Valid @RequestBody request: ComboRequest,
    ): ComboResponse = comboService.actualizar(id, request)

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> {
        comboService.eliminar(id)
        return ResponseEntity.noContent().build()
    }
}

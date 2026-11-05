package com.example.backend.controller

import com.example.backend.dto.PedidoRequest
import com.example.backend.dto.PedidoResponse
import com.example.backend.service.PedidoService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/pedidos")
class PedidoController(
    private val pedidoService: PedidoService,
) {
    @PostMapping
    fun crear(@Valid @RequestBody request: PedidoRequest): ResponseEntity<PedidoResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.crear(request))

    @GetMapping
    fun listar(): List<PedidoResponse> = pedidoService.listar()

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Int): PedidoResponse = pedidoService.obtener(id)
}

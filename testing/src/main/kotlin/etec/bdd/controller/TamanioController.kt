package etec.bdd.controller

import etec.bdd.dto.TamanioRequest
import etec.bdd.dto.TamanioResponse
import etec.bdd.service.TamanioService
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
@RequestMapping("/api/tamanios")
class TamanioController(
    private val tamanioService: TamanioService,
) {
    @GetMapping
    fun listar(): List<TamanioResponse> = tamanioService.listar()

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Int): TamanioResponse = tamanioService.obtener(id)

    @PostMapping
    fun crear(@Valid @RequestBody request: TamanioRequest): ResponseEntity<TamanioResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(tamanioService.crear(request))

    @PutMapping("/{id}")
    fun actualizar(
        @PathVariable id: Int,
        @Valid @RequestBody request: TamanioRequest,
    ): TamanioResponse = tamanioService.actualizar(id, request)

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> {
        tamanioService.eliminar(id)
        return ResponseEntity.noContent().build()
    }
}

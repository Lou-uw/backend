package etec.bdd.controller

import etec.bdd.dto.BordeRequest
import etec.bdd.dto.BordeResponse
import etec.bdd.service.BordeService
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
@RequestMapping("/api/bordes")
class BordeController(
    private val bordeService: BordeService,
) {
    @GetMapping
    fun listar(): List<BordeResponse> = bordeService.listar()

    @GetMapping("/{id}")
    fun obtener(@PathVariable id: Int): BordeResponse = bordeService.obtener(id)

    @PostMapping
    fun crear(@Valid @RequestBody request: BordeRequest): ResponseEntity<BordeResponse> =
        ResponseEntity.status(HttpStatus.CREATED).body(bordeService.crear(request))

    @PutMapping("/{id}")
    fun actualizar(
        @PathVariable id: Int,
        @Valid @RequestBody request: BordeRequest,
    ): BordeResponse = bordeService.actualizar(id, request)

    @DeleteMapping("/{id}")
    fun eliminar(@PathVariable id: Int): ResponseEntity<Void> {
        bordeService.eliminar(id)
        return ResponseEntity.noContent().build()
    }
}

package com.example.backend.controller

import com.example.backend.model.PrecondicionInvalidaException
import com.example.backend.service.CalculadoraDeDescuentos
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class DescuentoResponse(val montoFinal: Double,val error: String? = "")

@RestController
@RequestMapping("/api/descuentos")
class DescuentosEndpoint(
    private val calculadora: CalculadoraDeDescuentos,
) {
    @GetMapping
    fun calcular(
        @RequestParam("userID") userID: String,
        @RequestParam("monto") monto: Double,
    ): ResponseEntity<DescuentoResponse> {
        try {
            val valorFinal = calculadora.aplicarDescuentosSegunUsuario(userID,monto)
            return ResponseEntity.ok( DescuentoResponse(valorFinal))
        } catch (e: PrecondicionInvalidaException){
            return ResponseEntity.badRequest().body(DescuentoResponse(0.0, e.message))
        }
    }
    //DescuentoResponse(calculadora.aplicarDescuentosSegunUsuario(userID, monto))
}
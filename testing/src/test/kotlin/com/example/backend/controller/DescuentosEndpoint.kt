package com.example.backend.controller

import com.example.backend.service.CalculadoraDeDescuentos
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

data class DescuentoResponse(val montoFinal: Double)

@RestController
@RequestMapping("/api/descuentos")
class DescuentosEndpoint(
    private val calculadora: CalculadoraDeDescuentos,
) {
    @GetMapping
    fun calcular(
        @RequestParam("userID") userID: String,
        @RequestParam("monto") monto: Double,
    ): DescuentoResponse =
        DescuentoResponse(calculadora.aplicarDescuentosSegunUsuario(userID, monto))
}
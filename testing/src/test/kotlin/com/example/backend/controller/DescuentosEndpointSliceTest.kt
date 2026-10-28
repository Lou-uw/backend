package com.example.backend.controller

import com.ninjasquad.springmockk.MockkBean
import com.example.backend.service.CalculadoraDeDescuentos
import com.example.backend.model.PrecondicionInvalidaException
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@WebMvcTest(DescuentosEndpoint::class)
class DescuentosEndpointSliceTest(
    private val navegador: MockMvc,
    @MockkBean private val calculadora: CalculadoraDeDescuentos
) : BehaviorSpec({

    Given("el endpoint GET /api/descuentos") {

        And("un servicio que falla al incumplirse una precondición") {

            // Forzamos al mock a lanzar la excepción usando 'throws'
            every {
                calculadora.aplicarDescuentosSegunUsuario("123", -10.0)
            } throws PrecondicionInvalidaException("El monto debe ser mayor a cero")

            When("se hace la petición con el monto inválido") {
                val respuesta = navegador.perform(
                    get("/api/descuentos")
                        .param("userID", "123")
                        .param("monto", "-10.0")
                )

                Then("el controlador debe traducir la excepción a un estado HTTP 400 Bad Request") {
                    respuesta.andExpect(status().isBadRequest)
                }

                And("debe devolver un formato JSON estructurado con el mensaje de error") {
                    respuesta.andExpect(jsonPath("$.error").value("El monto debe ser mayor a cero"))
                }
            }
        }
    }
})
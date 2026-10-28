package com.example.backend.integration

import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest
@AutoConfigureMockMvc
class DescuentosIntegrationTest(
    private val navegador: MockMvc
) : BehaviorSpec({

    Given("el sistema completo funcionando con sus reglas de negocio reales") {

        When("un cliente envía un monto negativo (-100.0) a la API /api/descuentos") {
            val respuesta = navegador.perform(
                get("/api/descuentos")
                    .param("userID", "123")
                    .param("monto", "-100.0")
            )

            Then("la lógica real debe lanzar la excepción y el manejador global interceptarla devolviendo HTTP 400") {
                respuesta.andExpect(status().isBadRequest)
            }

            And("el cliente debe recibir la explicación del fallo en el cuerpo de la respuesta") {
                respuesta.andExpect(jsonPath("$.error").value("El monto debe ser mayor a cero"))
            }
        }
    }
})
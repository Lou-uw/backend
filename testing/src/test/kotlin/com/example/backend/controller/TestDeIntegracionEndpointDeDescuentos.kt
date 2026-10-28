package com.example.backend.controller

import io.kotest.core.spec.style.BehaviorSpec
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

@SpringBootTest // 1. Arranca el contexto completo de Spring Boot (Servicios, Repositorios, etc.)
@AutoConfigureMockMvc // 2. Configura e inyecta el cliente MockMvc para llamadas HTTP

class TestDeIntegracionEndpointDeDescuentos(
    private val navegador: MockMvc
    // 3. ¡Eliminado el @MockkBean! Ahora se ejecuta la lógica real de CalculadoraDeDescuentos
) : BehaviorSpec({

    Given("la aplicación cargada completamente con sus servicios reales") {

        And("un usuario '123' configurado en el sistema con 25% de descuento") {
            // NOTA: Ya no hay 'every'. Confiamos en los datos reales
            // o pre-cargados (por ejemplo, mediante un script de base de datos H2 o Setup)

            When("se hace una consulta GET /api/descuentos?userID=123&monto=100.0") {
                val respuesta =
                    navegador.perform(
                        get("/api/descuentos")
                            .param("userID", "123")
                            .param("monto", "100.0"),
                    )

                Then("deberia obtener respuesta con estado ok") {
                    respuesta.andExpect(status().isOk)
                }

                And("el monto final devuelto por la lógica real debe ser de 75.0") {
                    respuesta.andExpect(jsonPath("$.montoFinal").value(75.0))
                }
            }
        }
    }
})
package com.example.backend.controller

import com.ninjasquad.springmockk.MockkBean
import com.example.backend.service.CalculadoraDeDescuentos
import io.kotest.core.spec.style.BehaviorSpec
import io.mockk.every
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status

// 1. @WebMvcTest SOLO levanta la capa web (rutas, JSON, HTTP).
// No inicia el contexto completo de Spring Boot ni levanta repositorios ni servicios reales.
// (En un test de integración usaríamos @SpringBootTest + @AutoConfigureMockMvc).
@WebMvcTest(DescuentosEndpoint::class)
class TestDeEndpointDeDescuentos(
    private val navegador: MockMvc,

    // 2. Inyectamos un MOCK del servicio en el contexto reducido de Spring.
    // El código real dentro de CalculadoraDeDescuentos NUNCA se ejecutará en esta prueba.
    // (En integración eliminaríamos esta anotación para usar el bean real registrado por Spring).
    @MockkBean private val calculadora: CalculadoraDeDescuentos,
) : BehaviorSpec({

    Given("un endpoint para calcular descuentos /api/descuentos") {
        And("un usuario 123 al cual se le aplica un descuento del 25% sin importar el monto") {

            // 3. Simulamos/cableamos la respuesta manualmente con 'every'.
            // Este test evaluará si el Controller mapea bien el resultado del servicio, NO si la
            // lógica matemática del servicio es correcta.
            // (En integración NO se usa 'every': el servicio real procesará la regla o consultará la BD).
            every {
                calculadora.aplicarDescuentosSegunUsuario("123", 100.0)
            } returns 75.0

            When("se hace una consulta GET /api/descuentos?userID=123&monto=100.0") {

                // 4. Simulamos la llamada HTTP a través de MockMvc.
                //    Valida la extracción de parámetros de la URL ("userID" y "monto").
                val respuesta =
                    navegador.perform(
                        get("/api/descuentos")
                            .param("userID", "123")
                            .param("monto", "100.0"),
                    )

                Then("deberia obtener respuesta con estado ok") {
                    // Valida que el controlador devuelva un código HTTP 200 OK
                    respuesta.andExpect(status().isOk)
                }

                And("un campo para el final de la compra que deberia ser de 75.0") {
                    // Valida que el objeto devuelto por el controller se serialice correctamente a JSON
                    respuesta.andExpect(jsonPath("$.montoFinal").value(75.0))
                }
            }
        }
    }
})
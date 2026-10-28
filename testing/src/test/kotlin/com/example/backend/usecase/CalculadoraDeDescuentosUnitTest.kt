package com.example.backend.usecase

import com.example.backend.model.PrecondicionInvalidaException
import com.example.backend.service.CalculadoraDeDescuentos
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class CalculadoraDeDescuentosUnitTest  : BehaviorSpec({

    val calculadora = CalculadoraDeDescuentos()

    Given("la calculadora de descuentos") {

        When("se intenta aplicar un descuento a un monto negativo (-50.0)") {

            Then("debe lanzar la excepción PrecondicionInvalidaException") {
                // Capturamos y verificamos la excepción con Kotest
                val excepcion = shouldThrow<PrecondicionInvalidaException> {
                    calculadora.aplicarDescuentosSegunUsuario("123", -50.0)
                }

                // Opcional: También podemos validar el mensaje dentro de la excepción
                excepcion.message shouldBe "El monto debe ser mayor a cero"
            }
        }
    }
})
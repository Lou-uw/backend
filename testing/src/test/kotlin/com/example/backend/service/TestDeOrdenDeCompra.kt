package com.example.backend.service

import com.example.backend.model.Item
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.shouldBe

class TestDeOrdenDeCompra : BehaviorSpec({
    Given("una orden de compra cuya suma de items resulta en 100 y el costo de la manteca es de 20") {
        val item =
            Item("Un producto", 100)
        val orden = OrdenDeCompra()
        orden.agregarItem(item)

        When("Agrego una manteca a la orden") {
            orden.agregarItem(Item("Manteca", 20))

            Then("La suma de mis items será de 120") {
                orden.calcularTotal() shouldBe 120
            }
        }
    }
})
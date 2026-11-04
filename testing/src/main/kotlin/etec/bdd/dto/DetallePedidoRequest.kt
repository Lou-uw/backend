package etec.bdd.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull

data class DetallePedidoRequest(
    @field:NotNull
    val productoId: Int,

    val tamanioId: Int? = null,   // vacío en papas y bebidas
    val bordeId: Int? = null,     // vacío si no pidió borde

    @field:NotNull
    @field:Min(1)
    val cantidad: Int,

    val descripcion: String? = null,
)
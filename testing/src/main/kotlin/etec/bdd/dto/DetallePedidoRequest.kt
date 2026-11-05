package etec.bdd.dto

import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class DetallePedidoRequest(
    @field:NotNull
    val productoId: Int,

    val tamanioId: Int? = null,

    val bordeId: Int? = null,

    @field:NotNull
    @field:Min(1)
    val cantidad: Int,

    @field:Size(max = 300)
    val descripcion: String? = null,
)

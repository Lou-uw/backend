package etec.bdd.dto

import jakarta.validation.Valid
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size

data class PedidoRequest(
    @field:NotNull
    val clienteId: Int,

    @field:Size(max = 300)
    val direccionEntrega: String? = null,

    @field:NotEmpty
    @field:Valid
    val detalles: List<DetallePedidoRequest>,
)
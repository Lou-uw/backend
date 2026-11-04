package etec.bdd.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.NotNull
import java.math.BigDecimal

data class PrecioBordeRequest(
    @field:NotNull
    val bordeId: Int,

    @field:NotNull
    val tamanioId: Int,

    @field:NotNull
    @field:DecimalMin(value = "0.0", inclusive = true)
    val precio: BigDecimal,
)
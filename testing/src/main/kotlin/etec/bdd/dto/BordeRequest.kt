package etec.bdd.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class BordeRequest(
    @field:NotBlank
    @field:Size(min = 1, max = 50)
    val nombre: String,

    @field:NotNull
    val precioBase: BigDecimal,
)

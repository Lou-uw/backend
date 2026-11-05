package etec.bdd.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class TamanioRequest(
    @field:NotBlank
    @field:Size(min = 1, max = 30)
    val nombre: String,

    @field:NotNull
    val factorPrecio: BigDecimal,
)

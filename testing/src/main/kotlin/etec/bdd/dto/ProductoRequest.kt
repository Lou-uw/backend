package etec.bdd.dto

import jakarta.validation.constraints.DecimalMin
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import java.math.BigDecimal

data class ProductoRequest(
    @field:NotBlank
    @field:Size(min = 1, max = 100)
    val nombre: String,

    @field:NotNull
    val categoriaId: Int,

    val tipoSaborId: Int? = null,      // solo pizzas

    @field:DecimalMin(value = "0.0", inclusive = true)
    val precio: BigDecimal? = null,    // solo papas y bebidas

    @field:Min(0)
    val stock: Int? = null,

    val activo: Boolean = true,
)
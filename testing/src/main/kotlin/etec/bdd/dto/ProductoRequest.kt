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

    @field:Size(max = 500)
    val descripcion: String? = null,

    @field:Size(max = 500)
    val imagen: String? = null,

    @field:NotNull
    val categoriaId: Int,

    @field:NotNull
    @field:DecimalMin(value = "0.0", inclusive = true)
    val precioBase: BigDecimal,

    @field:Min(0)
    val stock: Int? = null,

    val activo: Boolean = true,
)

package etec.bdd.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class ClienteRequest(
    @field:NotBlank
    @field:Size(max = 100)
    val nombre: String,

    @field:Size(max = 100)
    val apellido: String? = null,

    @field:Size(max = 300)
    val direccion: String? = null,
)
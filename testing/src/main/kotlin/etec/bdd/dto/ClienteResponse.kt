package etec.bdd.dto

import etec.bdd.entity.Cliente

data class ClienteResponse(
    val id: Int,
    val nombre: String,
    val apellido: String?,
    val direccion: String?,
) {
    companion object {
        fun fromEntity(c: Cliente): ClienteResponse =
            ClienteResponse(
                id = c.id ?: 0,
                nombre = c.nombre ?: "",
                apellido = c.apellido,
                direccion = c.direccion,
            )
    }
}
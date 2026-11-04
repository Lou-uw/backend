package etec.bdd.dto

import etec.bdd.entity.Borde

data class BordeResponse(
    val id: Int,
    val nombre: String,
) {
    companion object {
        fun fromEntity(b: Borde): BordeResponse =
            BordeResponse(id = b.id ?: 0, nombre = b.nombre)
    }
}
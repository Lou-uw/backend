package etec.bdd.dto

import etec.bdd.entity.Tamanio

data class TamanioResponse(
    val id: Int,
    val nombre: String,
) {
    companion object {
        fun fromEntity(t: Tamanio): TamanioResponse =
            TamanioResponse(id = t.id ?: 0, nombre = t.nombre)
    }
}
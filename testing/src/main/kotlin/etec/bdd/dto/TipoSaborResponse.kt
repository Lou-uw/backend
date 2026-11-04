package etec.bdd.dto

import etec.bdd.entity.TipoSabor

data class TipoSaborResponse(
    val id: Int,
    val nombre: String,
) {
    companion object {
        fun fromEntity(t: TipoSabor): TipoSaborResponse =
            TipoSaborResponse(id = t.id ?: 0, nombre = t.nombre)
    }
}
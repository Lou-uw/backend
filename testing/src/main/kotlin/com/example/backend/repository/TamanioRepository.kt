package com.example.backend.repository

import com.example.backend.entity.Tamanio
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.Optional

@Repository
interface TamanioRepository : JpaRepository<Tamanio, Int> {
    fun findByNombre(nombre: String): Optional<Tamanio>
}

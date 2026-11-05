package com.example.backend.repository

import com.example.backend.entity.Producto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ProductoRepository : JpaRepository<Producto, Int> {
    fun findByActivoTrue(): List<Producto>
    fun findByCategoriaId(categoriaId: Int): List<Producto>
}

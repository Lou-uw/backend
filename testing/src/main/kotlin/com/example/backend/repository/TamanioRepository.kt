package com.example.backend.repository

import com.example.backend.entity.Tamanio
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TamanioRepository : JpaRepository<Tamanio, Int>
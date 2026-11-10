package com.example.backend.repository

import com.example.backend.entity.Combo
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface ComboRepository : JpaRepository<Combo, Int>

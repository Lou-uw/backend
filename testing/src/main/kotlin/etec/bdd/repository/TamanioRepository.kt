package etec.bdd.repository

import etec.bdd.entity.Tamanio
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TamanioRepository : JpaRepository<Tamanio, Int>
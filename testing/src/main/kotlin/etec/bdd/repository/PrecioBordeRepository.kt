package etec.bdd.repository

import etec.bdd.entity.PrecioBorde
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PrecioBordeRepository : JpaRepository<PrecioBorde, Int>
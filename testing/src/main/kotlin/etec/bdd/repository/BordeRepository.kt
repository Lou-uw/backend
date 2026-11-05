package etec.bdd.repository

import etec.bdd.entity.Borde
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface BordeRepository : JpaRepository<Borde, Int>

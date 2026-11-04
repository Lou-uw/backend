package etec.bdd.repository

import etec.bdd.entity.PrecioPizza
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface PrecioPizzaRepository : JpaRepository<PrecioPizza, Int>
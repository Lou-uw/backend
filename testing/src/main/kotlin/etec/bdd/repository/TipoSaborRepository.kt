package etec.bdd.repository

import etec.bdd.entity.TipoSabor
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TipoSaborRepository : JpaRepository<TipoSabor, Int>
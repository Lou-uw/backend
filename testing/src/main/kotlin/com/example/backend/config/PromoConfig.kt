package com.example.backend.config

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

/** Configuración central de promociones diarias. */
object PromoConfig {
    /** Zona horaria del local. Cambiar acá si se mueve el negocio de zona. */
    val zonaHoraria: ZoneId = ZoneId.of("America/Asuncion")

    /** Fecha de hoy en la zona horaria del local. */
    fun hoy(): LocalDate = ZonedDateTime.now(zonaHoraria).toLocalDate()

    /** Verdadero si la fecha indicada es miércoles. */
    fun esMiercoles(fecha: LocalDate): Boolean = fecha.dayOfWeek == DayOfWeek.WEDNESDAY

    /** Verdadero si la fecha indicada es viernes. */
    fun esViernes(fecha: LocalDate): Boolean = fecha.dayOfWeek == DayOfWeek.FRIDAY

    /** Verdadero si la fecha indicada es sábado o domingo. */
    fun esFinDeSemana(fecha: LocalDate): Boolean =
        fecha.dayOfWeek == DayOfWeek.SATURDAY || fecha.dayOfWeek == DayOfWeek.SUNDAY

    /** Verdadero si la fecha indicada tiene alguna promoción activa. */
    fun hayPromoActiva(fecha: LocalDate): Boolean =
        esMiercoles(fecha) || esViernes(fecha) || esFinDeSemana(fecha)
}

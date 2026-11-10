package com.example.backend.config

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

/** Configuración central de promociones diarias. */
object PromoConfig {
    /** Zona horaria del local. Cambiar acá si se mueve el negocio de zona. */
    val zonaHoraria: ZoneId = ZoneId.of("America/Asuncion")

    /**
     * Fecha forzada para pruebas. Si es null se usa la fecha real del servidor
     * convertida a la zona horaria del local.
     * Ejemplo para forzar miércoles: PromoConfig.fechaForzada = LocalDate.of(2026, 11, 11)
     */
    var fechaForzada: LocalDate? = null

    /** Fecha efectiva que se usa para evaluar promociones (zona horaria del local). */
    val fechaHoy: LocalDate
        get() = fechaForzada ?: ZonedDateTime.now(zonaHoraria).toLocalDate()

    /** Día de la semana efectivo. */
    val diaSemana: DayOfWeek
        get() = fechaHoy.dayOfWeek

    /** Verdadero si hoy es miércoles. */
    val esMiercoles: Boolean get() = diaSemana == DayOfWeek.WEDNESDAY

    /** Verdadero si hoy es viernes. */
    val esViernes: Boolean get() = diaSemana == DayOfWeek.FRIDAY

    /** Verdadero si hoy es sábado o domingo. */
    val esFinDeSemana: Boolean get() =
        diaSemana == DayOfWeek.SATURDAY || diaSemana == DayOfWeek.SUNDAY

    /** Verdadero si hoy tiene alguna promoción activa. */
    val hayPromoActiva: Boolean get() = esMiercoles || esViernes || esFinDeSemana
}

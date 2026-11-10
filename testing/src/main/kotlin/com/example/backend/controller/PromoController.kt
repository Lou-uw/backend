package com.example.backend.controller

import com.example.backend.config.PromoConfig
import com.example.backend.dto.PromoInfo
import com.example.backend.service.PedidoService
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.DayOfWeek
import java.time.LocalDate

@RestController
@RequestMapping("/api/promos")
class PromoController(private val pedidoService: PedidoService) {

    @GetMapping("/hoy")
    fun promoHoy(): PromoInfo {
        return pedidoService.promoHoy()
    }

    /** Obtener promo para un día específico de la semana (para pruebas y front). */
    @GetMapping("/dia")
    fun promoDia(@RequestParam dia: String): PromoInfo {
        val diaMap = mapOf(
            "LUNES" to DayOfWeek.MONDAY,
            "MARTES" to DayOfWeek.TUESDAY,
            "MIÉRCOLES" to DayOfWeek.WEDNESDAY,
            "MIERCOLES" to DayOfWeek.WEDNESDAY,
            "JUEVES" to DayOfWeek.THURSDAY,
            "VIERNES" to DayOfWeek.FRIDAY,
            "SÁBADO" to DayOfWeek.SATURDAY,
            "SABADO" to DayOfWeek.SATURDAY,
            "DOMINGO" to DayOfWeek.SUNDAY,
            "MONDAY" to DayOfWeek.MONDAY,
            "TUESDAY" to DayOfWeek.TUESDAY,
            "WEDNESDAY" to DayOfWeek.WEDNESDAY,
            "THURSDAY" to DayOfWeek.THURSDAY,
            "FRIDAY" to DayOfWeek.FRIDAY,
            "SATURDAY" to DayOfWeek.SATURDAY,
            "SUNDAY" to DayOfWeek.SUNDAY,
        )
        val diaUpper = dia.uppercase()
        val diaSemana = diaMap[diaUpper]
            ?: throw IllegalArgumentException("Día inválido: $dia")
        val fechaForzadaGuardada = PromoConfig.fechaForzada
        try {
            // Buscar una fecha que caiga en ese día de la semana
            var fecha = LocalDate.now()
            while (fecha.dayOfWeek != diaSemana) {
                fecha = fecha.plusDays(1)
            }
            PromoConfig.fechaForzada = fecha
            return pedidoService.promoHoy()
        } finally {
            PromoConfig.fechaForzada = fechaForzadaGuardada
        }
    }

    // --- Endpoints de prueba para forzar la fecha (solo desarrollo) ---
    @PostMapping("/test/forzar-fecha")
    fun forzarFecha(@RequestParam fecha: String): PromoInfo {
        PromoConfig.fechaForzada = LocalDate.parse(fecha)
        return pedidoService.promoHoy()
    }

    @DeleteMapping("/test/forzar-fecha")
    fun limpiarFechaForzada() {
        PromoConfig.fechaForzada = null
    }
}

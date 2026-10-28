package com.example.backend.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

data class Mensaje(val message: String)


@RestController
@RequestMapping("/status")

class Status {
    @GetMapping
    fun mostrarEstado(): ResponseEntity<Mensaje>{
        return ResponseEntity.ok(Mensaje("OK"))
    }
}
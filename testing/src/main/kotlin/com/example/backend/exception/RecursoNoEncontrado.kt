package com.example.backend.exception

class RecursoNoEncontradoException(recurso: String, id: Int) :
    RuntimeException("$recurso no encontrado con el ID: $id")
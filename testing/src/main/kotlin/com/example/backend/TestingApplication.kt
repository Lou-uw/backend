package com.example.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class TestingApplication

fun main(args: Array<String>) {
    runApplication<TestingApplication>(*args)
}

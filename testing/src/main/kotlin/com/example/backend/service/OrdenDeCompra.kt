package com.example.backend.service

import com.example.backend.model.Item

class OrdenDeCompra {
    private val items = mutableListOf<Item>()
    fun agregarItem(item: Item) {
        this.items.add(item)
    }
    fun calcularTotal(): Int = this.items.sumOf { it.precio }
}
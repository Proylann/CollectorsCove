package com.example.collectorscove.data.model

data class Order(
    val id: String = "",
    val buyerId: String = "",
    val itemId: String = "",
    val itemName: String = "",
    val itemPrice: Double = 0.0,
    val status: String = "Pending",
    val timestamp: Long = System.currentTimeMillis()
)
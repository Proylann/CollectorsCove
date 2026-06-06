package com.example.collectorscove.data.model

data class CollectibleItem(
    val id: String = "",
    val sellerId: String = "",
    val name: String = "",
    val price: Double = 0.0,
    val category: String = "All",
    val description: String = "",
    val imageUrl: String = "",
    val isSold: Boolean = false,
    val timestamp: Long = 0L
)

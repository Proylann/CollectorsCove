package com.example.collectorscove.data.model

data class User(
    val uid: String = "",
    val email: String = "",
    val firstName: String = "",
    val lastName: String = "",
    val phoneNumber: String = "",
    val address: String = "",
    val nationality: String = "",
    val gender: String = "",
    val createdAt: Long = System.currentTimeMillis()
)
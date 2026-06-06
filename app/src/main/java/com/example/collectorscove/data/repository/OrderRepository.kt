package com.example.collectorscove.data.repository

import com.example.collectorscove.data.firebase.FirebaseManager
import com.example.collectorscove.data.model.Order
import com.google.firebase.firestore.Query

class OrderRepository {
    private val firestore = FirebaseManager.firestore

    fun placeOrder(order: Order, onResult: (Boolean, String?) -> Unit) {
        val docRef = firestore.collection("orders").document()
        val orderWithId = order.copy(id = docRef.id)
        docRef.set(orderWithId)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { onResult(false, it.message) }
    }

    fun getOrdersForUser(userId: String, onResult: (List<Order>) -> Unit) {
        // Fetch all orders for this user and sort in code to avoid index requirement
        firestore.collection("orders")
            .whereEqualTo("buyerId", userId)
            .get()
            .addOnSuccessListener { snapshot ->
                val orders = snapshot.toObjects(Order::class.java)
                    .sortedByDescending { it.timestamp }
                onResult(orders)
            }
            .addOnFailureListener { onResult(emptyList()) }
    }
}
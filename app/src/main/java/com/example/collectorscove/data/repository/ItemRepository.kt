package com.example.collectorscove.data.repository

import android.content.Context
import android.net.Uri
import com.example.collectorscove.data.firebase.FirebaseManager
import com.example.collectorscove.data.model.CollectibleItem
import java.io.File
import java.io.FileOutputStream

class ItemRepository {
    private val firestore = FirebaseManager.firestore

    fun saveImageLocally(context: Context, uri: Uri, onResult: (String?) -> Unit) {
        try {
            val directory = File(context.filesDir, "item_images")
            if (!directory.exists()) {
                directory.mkdirs()
            }

            val fileName = "item_${System.currentTimeMillis()}.jpg"
            val file = File(directory, fileName)

            context.contentResolver.openInputStream(uri)?.use { input ->
                FileOutputStream(file).use { output ->
                    input.copyTo(output)
                }
            }
            onResult(file.absolutePath)
        } catch (e: Exception) {
            e.printStackTrace()
            onResult(null)
        }
    }

    fun postItem(item: CollectibleItem, onResult: (Boolean, String?) -> Unit) {
        val docRef = firestore.collection("items").document()
        val itemWithId = item.copy(id = docRef.id, timestamp = System.currentTimeMillis())
        docRef.set(itemWithId)
            .addOnSuccessListener { onResult(true, null) }
            .addOnFailureListener { onResult(false, it.message) }
    }

    fun markItemAsSold(itemId: String, onResult: (Boolean) -> Unit) {
        firestore.collection("items").document(itemId)
            .update("isSold", true)
            .addOnSuccessListener { onResult(true) }
            .addOnFailureListener { onResult(false) }
    }

    fun getAllItems(onResult: (List<CollectibleItem>) -> Unit) {
        // Using addSnapshotListener for real-time updates without needing to refresh
        firestore.collection("items")
            .addSnapshotListener { snapshot, error ->
                if (error != null) {
                    onResult(emptyList())
                    return@addSnapshotListener
                }
                
                val items = snapshot?.toObjects(CollectibleItem::class.java) ?: emptyList()
                val activeItems = items.filter { !it.isSold }
                    .sortedByDescending { it.timestamp }
                onResult(activeItems)
            }
    }

    fun getItemsByCategory(category: String, onResult: (List<CollectibleItem>) -> Unit) {
        val baseQuery = if (category == "All") {
            firestore.collection("items")
        } else {
            firestore.collection("items").whereEqualTo("category", category)
        }
        
        baseQuery.get()
            .addOnSuccessListener { snapshot ->
                val items = snapshot.toObjects(CollectibleItem::class.java)
                    .filter { !it.isSold }
                    .sortedByDescending { it.timestamp }
                onResult(items)
            }
            .addOnFailureListener { onResult(emptyList()) }
    }
}
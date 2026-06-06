package com.example.collectorscove.ui.item

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.collectorscove.data.model.CollectibleItem
import com.example.collectorscove.data.repository.ItemRepository

class ItemViewModel : ViewModel() {
    private val itemRepository = ItemRepository()

    private val _items = mutableStateOf<List<CollectibleItem>>(emptyList())
    val items: State<List<CollectibleItem>> = _items

    init {
        fetchItems()
    }

    fun fetchItems() {
        itemRepository.getAllItems { itemList ->
            _items.value = itemList
        }
    }

    fun fetchItemsByCategory(category: String) {
        itemRepository.getItemsByCategory(category) { itemList ->
            _items.value = itemList
        }
    }

    fun postItem(
        context: android.content.Context,
        sellerId: String,
        name: String,
        price: Double,
        category: String,
        description: String,
        imageUri: android.net.Uri? = null,
        onResult: (Boolean, String?) -> Unit
    ) {
        if (imageUri != null) {
            itemRepository.saveImageLocally(context, imageUri) { localPath ->
                val newItem = CollectibleItem(
                    sellerId = sellerId,
                    name = name,
                    price = price,
                    category = category,
                    description = description,
                    imageUrl = localPath ?: ""
                )
                itemRepository.postItem(newItem) { success, error ->
                    if (success) fetchItems()
                    onResult(success, error)
                }
            }
        } else {
            val newItem = CollectibleItem(
                sellerId = sellerId,
                name = name,
                price = price,
                category = category,
                description = description
            )
            itemRepository.postItem(newItem) { success, error ->
                if (success) fetchItems()
                onResult(success, error)
            }
        }
    }

    fun markAsSold(itemId: String) {
        itemRepository.markItemAsSold(itemId) { success ->
            if (success) fetchItems()
        }
    }
}

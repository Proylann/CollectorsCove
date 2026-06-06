package com.example.collectorscove.ui.order

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.collectorscove.data.model.Order
import com.example.collectorscove.data.repository.OrderRepository
import com.example.collectorscove.ui.item.ItemViewModel

class OrderViewModel : ViewModel() {
    private val orderRepository = OrderRepository()

    private val _orders = mutableStateOf<List<Order>>(emptyList())
    val orders: State<List<Order>> = _orders

    fun fetchOrders(userId: String) {
        orderRepository.getOrdersForUser(userId) { orderList ->
            _orders.value = orderList
        }
    }

    fun placeOrder(
        buyerId: String,
        itemId: String,
        itemName: String,
        itemPrice: Double,
        itemViewModel: ItemViewModel,
        onResult: (Boolean, String?) -> Unit
    ) {
        val newOrder = Order(
            buyerId = buyerId,
            itemId = itemId,
            itemName = itemName,
            itemPrice = itemPrice
        )
        orderRepository.placeOrder(newOrder) { success, error ->
            if (success) {
                itemViewModel.markAsSold(itemId)
            }
            onResult(success, error)
        }
    }
}
package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectorscove.data.model.Order
import com.example.collectorscove.ui.auth.AuthViewModel
import com.example.collectorscove.ui.order.OrderViewModel
import com.example.collectorscove.ui.theme.CoveBackground
import com.example.collectorscove.ui.theme.CoveGold
import com.example.collectorscove.ui.theme.CoveSurface
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun OrderScreen(
    authViewModel: AuthViewModel,
    orderViewModel: OrderViewModel,
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    val orders by orderViewModel.orders
    val currentUser by authViewModel.currentUser

    LaunchedEffect(currentUser) {
        currentUser?.let { orderViewModel.fetchOrders(it.uid) }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoveBackground)
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        AppTopBar(
            onMenuClick = onMenuClick,
            onNotificationsClick = onNotificationsClick
        )
        
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "My Orders",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        
        Spacer(modifier = Modifier.height(8.dp))

        if (orders.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No orders yet", color = Color.Gray)
            }
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(bottom = 16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(orders) { order ->
                    OrderListItem(order = order)
                }
            }
        }
    }
}

@Composable
fun OrderListItem(order: Order) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CoveSurface)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = order.itemName,
                    fontWeight = FontWeight.Bold,
                    fontSize = 16.sp
                )
                Text(
                    text = "Status: ${order.status}",
                    fontSize = 14.sp,
                    color = CoveGold
                )
                
                val date = Date(order.timestamp)
                val format = SimpleDateFormat("MMM dd, yyyy", Locale.getDefault())
                Text(
                    text = format.format(date),
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            }
            Text(
                text = "P${String.format(Locale.getDefault(), "%,.2f", order.itemPrice)}",
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp
            )
        }
    }
}
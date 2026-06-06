package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Groups
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.collectorscove.data.model.CollectibleItem
import com.example.collectorscove.ui.auth.AuthViewModel
import com.example.collectorscove.ui.item.ItemViewModel
import com.example.collectorscove.ui.order.OrderViewModel
import com.example.collectorscove.ui.theme.CoveBackground
import com.example.collectorscove.ui.theme.CoveGold
import com.example.collectorscove.ui.theme.CoveSurface

@Composable
fun HomeScreen(
    authViewModel: AuthViewModel,
    itemViewModel: ItemViewModel,
    orderViewModel: OrderViewModel,
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    val items by itemViewModel.items
    val currentUser by authViewModel.currentUser
    var selectedItem by remember { mutableStateOf<CollectibleItem?>(null) }

    val context = androidx.compose.ui.platform.LocalContext.current

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
        
        SectionHeader(title = "New Arrivals", actionText = "See All")
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(items) { item ->
                ItemCard(
                    item = item,
                    onClick = { selectedItem = item }
                )
            }
        }
    }

    if (selectedItem != null) {
        ItemDetailDialog(
            item = selectedItem!!,
            currentUserUid = currentUser?.uid,
            onDismiss = { selectedItem = null },
            onOrder = {
                currentUser?.let { user ->
                    orderViewModel.placeOrder(
                        buyerId = user.uid,
                        itemId = selectedItem!!.id,
                        itemName = selectedItem!!.name,
                        itemPrice = selectedItem!!.price,
                        itemViewModel = itemViewModel
                    ) { success, error ->
                        if (success) {
                            android.widget.Toast.makeText(context, "Order placed successfully!", android.widget.Toast.LENGTH_SHORT).show()
                            selectedItem = null
                        } else {
                            android.widget.Toast.makeText(context, "Error: $error", android.widget.Toast.LENGTH_SHORT).show()
                        }
                    }
                }
            }
        )
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem(Screen.Home, Icons.Default.Home, "Home"),
        NavigationItem(Screen.Explore, Icons.Default.Explore, "Explore"),
        NavigationItem(Screen.CommunityFeed, Icons.Default.Groups, "Feed"),
        NavigationItem(Screen.Orders, Icons.Default.ShoppingCart, "Orders"),
        NavigationItem(Screen.Chat, Icons.Default.Chat, "Chat"),
        NavigationItem(Screen.Account, Icons.Default.AccountCircle, "Account")
    )

    NavigationBar(
        containerColor = CoveSurface,
        tonalElevation = 0.dp
    ) {
        val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

        items.forEach { item ->
            val selected = currentRoute == item.screen.route
            NavigationBarItem(
                selected = selected,
                onClick = {
                    navController.navigate(item.screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        imageVector = item.icon,
                        contentDescription = item.label,
                        tint = if (selected) Color.White else Color.Black
                    )
                },
                label = {
                    Text(
                        text = item.label,
                        fontSize = 10.sp,
                        color = Color.Black
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = CoveGold
                )
            )
        }
    }
}

private data class NavigationItem(
    val screen: Screen,
    val icon: ImageVector,
    val label: String
)

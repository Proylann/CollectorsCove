package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.collectorscove.ui.theme.CoveBackground
import com.example.collectorscove.ui.theme.CoveGold
import com.example.collectorscove.ui.theme.CoveSurface

@Composable
fun HomeScreen(
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(CoveBackground)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item {
            AppTopBar(
                onMenuClick = onMenuClick,
                onNotificationsClick = onNotificationsClick
            )
        }
        item { SectionHeader(title = "Hot Picks - Shoes", actionText = "See All") }
        item { ProductGrid(items = shoeItems) }
        item { SectionHeader(title = "Hot Picks - Cards", actionText = "See All") }
        item { ProductGrid(items = cardItems) }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    val items = listOf(
        NavigationItem(Screen.Home, Icons.Default.Home, "Home"),
        NavigationItem(Screen.Explore, Icons.Default.Explore, "Explore"),
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

private val shoeItems = listOf(
    CollectibleItem(
        imageLabel = "Sneaker",
        name = "Nike SB Dunk Low Hein...",
        price = "P335,800.00",
        imageColor = Color(0xFFE8F0ED)
    ),
    CollectibleItem(
        imageLabel = "Sneaker",
        name = "7-Eleven x Nike SB Dunk...",
        price = "P310,169.34",
        imageColor = Color(0xFFE8EEF0)
    ),
    CollectibleItem(
        imageLabel = "Sneaker",
        name = "Nike Dunk High Coraline",
        price = "P333,900.65",
        imageColor = Color(0xFFF0EDE8)
    )
)

private val cardItems = listOf(
    CollectibleItem(
        imageLabel = "Card",
        name = "Charizard-Holo 2016...",
        price = "P143,671.00",
        imageColor = Color(0xFFFFF2E0)
    ),
    CollectibleItem(
        imageLabel = "Card",
        name = "Pokemon Fossil Rare...",
        price = "P10,891.18",
        imageColor = Color(0xFFEDE7F6)
    ),
    CollectibleItem(
        imageLabel = "Card",
        name = "Solgaleo-GX - 155/149...",
        price = "P3,954.86",
        imageColor = Color(0xFFE7E0D4)
    )
)

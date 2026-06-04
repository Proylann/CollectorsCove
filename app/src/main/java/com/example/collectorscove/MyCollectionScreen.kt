package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectorscove.ui.theme.CoveBackground
import com.example.collectorscove.ui.theme.CoveTextSecondary

@Composable
fun MyCollectionScreen(
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
        item {
            Column {
                Text(
                    text = "My Collection",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Items you own and collect",
                    fontSize = 13.sp,
                    color = CoveTextSecondary
                )
            }
        }
        item { ProductGrid(items = myCollectionItems) }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

private val myCollectionItems = listOf(
    CollectibleItem(
        imageLabel = "Card",
        name = "Charizard-Holo 2016 Pokemon",
        price = "P143,671.00",
        imageColor = Color(0xFFFFF2E0)
    ),
    CollectibleItem(
        imageLabel = "Sneaker",
        name = "Nike SB Dunk Low Heineken",
        price = "P335,800.00",
        imageColor = Color(0xFFE8F0ED)
    ),
    CollectibleItem(
        imageLabel = "Toy",
        name = "Vintage Tin Robot Collectible",
        price = "P8,450.00",
        imageColor = Color(0xFFEDE7F6)
    )
)

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

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.collectorscove.data.model.CollectibleItem
import com.example.collectorscove.ui.auth.AuthViewModel
import com.example.collectorscove.ui.item.ItemViewModel
import com.example.collectorscove.ui.theme.CoveBackground
import com.example.collectorscove.ui.theme.CoveTextSecondary

@Composable
fun MyCollectionScreen(
    authViewModel: AuthViewModel,
    itemViewModel: ItemViewModel = viewModel(),
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    val items by itemViewModel.items
    val currentUser by authViewModel.currentUser
    
    val myItems = items.filter { it.sellerId == currentUser?.uid }
    var selectedItem by remember { mutableStateOf<CollectibleItem?>(null) }

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
        Column {
            Text(
                text = "My Collection",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Items you are currently selling",
                fontSize = 13.sp,
                color = CoveTextSecondary
            )
        }
        Spacer(modifier = Modifier.height(16.dp))
        
        if (myItems.isEmpty()) {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No items listed yet", color = Color.Gray)
            }
        } else {
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                modifier = Modifier.weight(1f),
                contentPadding = PaddingValues(bottom = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                items(myItems) { item ->
                    ItemCard(
                        item = item,
                        onClick = { selectedItem = item }
                    )
                }
            }
        }
    }
    
    if (selectedItem != null) {
        ItemDetailDialog(
            item = selectedItem!!,
            currentUserUid = currentUser?.uid,
            onDismiss = { selectedItem = null },
            onOrder = { /* Owner can't order their own items */ }
        )
    }
}

package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectorscove.data.model.CollectibleItem
import com.example.collectorscove.ui.auth.AuthViewModel
import com.example.collectorscove.ui.item.ItemViewModel
import com.example.collectorscove.ui.order.OrderViewModel
import com.example.collectorscove.ui.theme.CoveBackground
import com.example.collectorscove.ui.theme.CoveBorder
import com.example.collectorscove.ui.theme.CoveGold
import com.example.collectorscove.ui.theme.CoveLightGray
import com.example.collectorscove.ui.theme.CoveSurface

@Composable
fun ExploreScreen(
    authViewModel: AuthViewModel,
    itemViewModel: ItemViewModel,
    orderViewModel: OrderViewModel,
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }
    
    val items by itemViewModel.items
    val currentUser by authViewModel.currentUser
    var selectedItem by remember { mutableStateOf<CollectibleItem?>(null) }

    val filteredItems = items.filter { 
        (selectedCategory == "All" || it.category == selectedCategory) &&
        (query.isBlank() || it.name.contains(query, ignoreCase = true))
    }

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
        SearchBar(
            query = query,
            onQueryChange = { query = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        CategoryChips(
            selectedCategory = selectedCategory,
            onCategorySelected = { selectedCategory = it }
        )
        Spacer(modifier = Modifier.height(16.dp))
        
        SectionHeader(title = if (query.isBlank()) "Popular Results" else "Search Results")
        
        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            items(filteredItems.chunked(2)) { rowItems ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    for (item in rowItems) {
                        Box(modifier = Modifier.weight(1f)) {
                            ItemCard(item = item, onClick = { selectedItem = item })
                        }
                    }
                    if (rowItems.size == 1) {
                        Spacer(modifier = Modifier.weight(1f))
                    }
                }
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
private fun SearchBar(
    query: String,
    onQueryChange: (String) -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = query,
            onValueChange = onQueryChange,
            placeholder = {
                Text(
                    text = "Search collectibles",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            },
            leadingIcon = {
                Text("Search", fontSize = 10.sp, color = Color.Gray)
            },
            modifier = Modifier
                .weight(1f)
                .height(50.dp),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = CoveGold,
                unfocusedBorderColor = CoveBorder,
                focusedContainerColor = CoveSurface,
                unfocusedContainerColor = CoveSurface,
                cursorColor = Color.Black
            )
        )
    }
}

@Composable
private fun CategoryChips(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("All", "Shoes", "Cards", "Toys", "Antiques")

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(categories) { category ->
            val selected = category == selectedCategory

            Box(
                modifier = Modifier
                    .height(32.dp)
                    .background(
                        color = if (selected) CoveGold else CoveLightGray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (selected) CoveGold else CoveBorder,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .clickable { onCategorySelected(category) }
                    .padding(horizontal = 12.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = category,
                    fontSize = 12.sp,
                    color = if (selected) Color.White else Color.Black
                )
            }
        }
    }
}

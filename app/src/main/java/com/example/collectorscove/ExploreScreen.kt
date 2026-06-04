package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectorscove.ui.theme.CoveBackground
import com.example.collectorscove.ui.theme.CoveBorder
import com.example.collectorscove.ui.theme.CoveGold
import com.example.collectorscove.ui.theme.CoveLightGray
import com.example.collectorscove.ui.theme.CoveSurface

@Composable
fun ExploreScreen(
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    var query by remember { mutableStateOf("") }
    var selectedCategory by remember { mutableStateOf("All") }

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
            SearchBar(
                query = query,
                onQueryChange = { query = it }
            )
        }
        item {
            CategoryChips(
                selectedCategory = selectedCategory,
                onCategorySelected = { selectedCategory = it }
            )
        }
        item { SectionHeader(title = "Recent Searches") }
        item { RecentSearches() }
        item { SectionHeader(title = "Popular Results") }
        item { ProductGrid(items = popularItems) }
        item { Spacer(modifier = Modifier.height(24.dp)) }
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

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .size(50.dp)
                .border(1.dp, CoveBorder, RoundedCornerShape(8.dp))
                .background(CoveSurface, RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text("Filter", fontSize = 11.sp, color = Color.Black)
        }
    }
}

@Composable
private fun CategoryChips(
    selectedCategory: String,
    onCategorySelected: (String) -> Unit
) {
    val categories = listOf("All", "Shoes", "Cards", "Toys", "Antiques")

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        categories.forEach { category ->
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

@Composable
private fun RecentSearches() {
    Column(
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        listOf("Nike Air Max", "Charizard", "Vintage radio").forEach { search ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(36.dp)
                    .background(CoveLightGray, RoundedCornerShape(6.dp))
                    .padding(horizontal = 12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = search,
                    modifier = Modifier.weight(1f),
                    fontSize = 13.sp,
                    color = Color.Black
                )

                Text(
                    text = "x",
                    fontSize = 13.sp,
                    color = Color.Gray
                )
            }
        }
    }
}

private val popularItems = listOf(
    CollectibleItem(
        imageLabel = "Sneaker",
        name = "MSCHF x INRI x Nike Air Max 97",
        price = "P335,800.00",
        imageColor = Color(0xFFE8F0ED)
    ),
    CollectibleItem(
        imageLabel = "Card",
        name = "Charizard-Holo 2016 Pokemon",
        price = "P143,671.00",
        imageColor = Color(0xFFFFF2E0)
    ),
    CollectibleItem(
        imageLabel = "Toy",
        name = "Vintage Tin Robot Collectible",
        price = "P8,450.00",
        imageColor = Color(0xFFEDE7F6)
    ),
    CollectibleItem(
        imageLabel = "Radio",
        name = "Classic Wooden Radio",
        price = "P5,731.63",
        imageColor = Color(0xFFE7E0D4)
    )
)

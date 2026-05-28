package com.example.collectorscove

import androidx.compose.foundation.BorderStroke
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val ExploreGold = Color(0xFFB8970A)
private val ExploreLightGray = Color(0xFFF3F3F3)
private val ExploreBorder = Color(0xFFD8D8D8)

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
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        item { Spacer(modifier = Modifier.height(24.dp)) }
        item { 
            ExploreTopBar(
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
        item { SectionTitle("Recent Searches") }
        item { RecentSearches() }
        item { SectionTitle("Popular Results") }
        item { PopularResultsGrid() }
        item { Spacer(modifier = Modifier.height(24.dp)) }
    }
}

@Composable
private fun ExploreTopBar(
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = Icons.Default.Menu,
            contentDescription = "Menu",
            modifier = Modifier
                .size(28.dp)
                .clickable(onClick = onMenuClick),
            tint = Color.Black
        )

        Spacer(modifier = Modifier.weight(1f))

        LogoMark()

        Spacer(modifier = Modifier.weight(1f))

        Icon(
            imageVector = Icons.Default.Notifications,
            contentDescription = "Notifications",
            modifier = Modifier
                .size(24.dp)
                .clickable(onClick = onNotificationsClick),
            tint = Color.Black
        )
    }
}

@Composable
private fun LogoMark() {
    Box(
        modifier = Modifier.width(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.CenterStart)
                .background(ExploreGold, CircleShape)
        )

        Column(
            modifier = Modifier.padding(start = 22.dp)
        ) {
            Text(
                text = "Collectors",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 10.sp,
                color = Color.Black
            )
            Text(
                text = "Cove",
                fontSize = 12.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 10.sp,
                color = Color.Black
            )
            Text(
                text = "est. 2024",
                fontSize = 4.sp,
                color = Color.Black
            )
        }
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
                focusedBorderColor = ExploreGold,
                unfocusedBorderColor = ExploreBorder,
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                cursorColor = Color.Black
            )
        )

        Spacer(modifier = Modifier.width(10.dp))

        Box(
            modifier = Modifier
                .size(50.dp)
                .border(1.dp, ExploreBorder, RoundedCornerShape(8.dp))
                .background(Color.White, RoundedCornerShape(8.dp)),
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
                        color = if (selected) ExploreGold else ExploreLightGray,
                        shape = RoundedCornerShape(16.dp)
                    )
                    .border(
                        width = 1.dp,
                        color = if (selected) ExploreGold else ExploreBorder,
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
private fun SectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 18.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
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
                    .background(ExploreLightGray, RoundedCornerShape(6.dp))
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

@Composable
private fun PopularResultsGrid() {
    Column(
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        popularItems.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { item ->
                    PopularResultCard(
                        item = item,
                        modifier = Modifier.weight(1f)
                    )
                }

                if (rowItems.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun PopularResultCard(
    item: PopularItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(178.dp),
        shape = RoundedCornerShape(6.dp),
        border = BorderStroke(1.dp, ExploreBorder),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(104.dp)
                    .background(item.color),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.imageLabel,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.DarkGray
                )

                Text(
                    text = "Heart",
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(8.dp),
                    fontSize = 10.sp,
                    color = Color.Black
                )
            }

            Column(
                modifier = Modifier.padding(8.dp)
            ) {
                Text(
                    text = item.name,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = item.price,
                    fontSize = 12.sp,
                    color = ExploreGold,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

private data class PopularItem(
    val imageLabel: String,
    val name: String,
    val price: String,
    val color: Color
)

private val popularItems = listOf(
    PopularItem(
        imageLabel = "Sneaker",
        name = "MSCHF x INRI x Nike Air Max 97",
        price = "P335,800.00",
        color = Color(0xFFE8F0ED)
    ),
    PopularItem(
        imageLabel = "Card",
        name = "Charizard-Holo 2016 Pokemon",
        price = "P143,671.00",
        color = Color(0xFFFFF2E0)
    ),
    PopularItem(
        imageLabel = "Toy",
        name = "Vintage Tin Robot Collectible",
        price = "P8,450.00",
        color = Color(0xFFEDE7F6)
    ),
    PopularItem(
        imageLabel = "Radio",
        name = "Classic Wooden Radio",
        price = "P5,731.63",
        color = Color(0xFFE7E0D4)
    )
)

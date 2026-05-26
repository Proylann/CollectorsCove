package com.example.collectorscove

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight

@Composable
fun ExploreScreen() {
    androidx.compose.material3.Scaffold(
        bottomBar = { ExploreBottomNavBar() },
        containerColor = Color.White
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(28.dp)) }
            item { ExploreTopBar() }
            item { ExploreHeader() }
            item { ExploreSectionTitle("Varieties") }
            item { ImageGrid(varietyItems) }
            item { ExploreSectionTitle("Metalware") }
            item { MetalwareRow() }
            item { Spacer(modifier = Modifier.height(40.dp)) }
        }
    }
}

@Composable
private fun ExploreTopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("☰", fontSize = 28.sp, color = Color.Black)

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.width(92.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterStart)
                    .background(Color(0xFFBFA619), shape = androidx.compose.foundation.shape.CircleShape)
            )

            Column(
                modifier = Modifier.padding(start = 20.dp)
            ) {
                Text(
                    text = "Collectors",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 10.sp
                )
                Text(
                    text = "Cove",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold,
                    lineHeight = 10.sp
                )
                Text(
                    text = "est. 2024",
                    fontSize = 4.sp
                )
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text("⌕", fontSize = 26.sp, color = Color.Black)
        Spacer(modifier = Modifier.width(16.dp))
        Text("♧", fontSize = 22.sp, color = Color.Black)
    }
}

@Composable
private fun ExploreHeader() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "Explore",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier
                .width(82.dp)
                .height(30.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(4.dp)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Discover  ⌄",
                fontSize = 12.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun ExploreSectionTitle(title: String) {
    Text(
        text = title,
        fontSize = 13.sp,
        fontWeight = FontWeight.Bold,
        color = Color.Black
    )
}

@Composable
private fun ImageGrid(items: List<String>) {
    Column(
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        items.chunked(3).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                rowItems.forEach { item ->
                    ExploreImageCard(
                        label = item,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
        }
    }
}

@Composable
private fun MetalwareRow() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(2.dp, Color(0xFF2196F3)),
        horizontalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        listOf("trophy", "bell", "teapot").forEach { item ->
            ExploreImageCard(
                label = item,
                modifier = Modifier.weight(1f),
                height = 72
            )
        }
    }
}

@Composable
private fun ExploreImageCard(
    label: String,
    modifier: Modifier = Modifier,
    height: Int = 72
) {
    Card(
        modifier = modifier.height(height.dp),
        shape = RoundedCornerShape(2.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFEFEFEF))
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color(0xFFE8E1D3)),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = label,
                fontSize = 11.sp,
                color = Color.DarkGray
            )

            // Replace the placeholder text above with an Image later:
            //
            // Image(
            //     painter = painterResource(id = R.drawable.your_image_name),
            //     contentDescription = label,
            //     modifier = Modifier.fillMaxSize(),
            //     contentScale = ContentScale.Crop
            // )
        }
    }
}

@Composable
private fun ExploreBottomNavBar() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        ExploreBottomNavItem("⌂", "Home", false)
        ExploreBottomNavItem("◉", "Explore", true)
        ExploreBottomNavItem("▾", "Orders", false)
        ExploreBottomNavItem("☏", "Chat", false)
        ExploreBottomNavItem("♡", "Account", false)
    }
}

@Composable
private fun RowScope.ExploreBottomNavItem(
    icon: String,
    label: String,
    selected: Boolean
) {
    NavigationBarItem(
        selected = selected,
        onClick = {},
        icon = {
            Text(
                text = icon,
                fontSize = 22.sp,
                color = Color.Black
            )
        },
        label = {
            Text(
                text = label,
                fontSize = 8.sp,
                color = Color.Black
            )
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent
        )
    )
}

private val varietyItems = listOf(
    "cards",
    "toys",
    "poster",
    "radio",
    "comics",
    "books",
    "fossil",
    "autograph",
    "armor"
)
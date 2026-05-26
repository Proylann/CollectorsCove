package com.example.collectorscove

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun HomeScreen() {
    Scaffold(
        bottomBar = { BottomNavBar() },
        containerColor = Color.White
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 20.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item { Spacer(modifier = Modifier.height(28.dp)) }
            item { TopBar() }
            item { CollectorHighlight() }
            item { ProductSection(title = "Shoes", items = shoeItems) }
            item { ProductSection(title = "Cards", items = cardItems) }
            item { Spacer(modifier = Modifier.height(20.dp)) }
        }
    }
}

@Composable
private fun TopBar() {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text("☰", fontSize = 32.sp)

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Collectors\nCove",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold,
            lineHeight = 13.sp
        )

        Spacer(modifier = Modifier.weight(1f))

        Text("⌕", fontSize = 30.sp)
        Spacer(modifier = Modifier.width(20.dp))
        Text("♧", fontSize = 24.sp)
    }
}

@Composable
private fun CollectorHighlight() {
    Column {
        Text(
            text = "Collector Highlight",
            fontSize = 15.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(176.dp)
                .border(1.dp, Color.Gray, RoundedCornerShape(8.dp))
                .clip(RoundedCornerShape(8.dp)),
            contentAlignment = Alignment.Center
        ) {
            // Replace this with your real image once added to res/drawable:
            // Image(
            //     painter = painterResource(id = R.drawable.air_max_97),
            //     contentDescription = "Nike Air Max 97",
            //     modifier = Modifier.fillMaxSize(),
            //     contentScale = ContentScale.Fit
            // )

            Text(
                text = "Add Air Max image here",
                color = Color.Gray,
                fontSize = 14.sp
            )
        }

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = "MSCHF x INRI x Nike Air Max 97",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            fontSize = 14.sp
        )

        Text(
            text = "•••",
            modifier = Modifier.align(Alignment.CenterHorizontally),
            color = Color.LightGray,
            fontSize = 20.sp
        )
    }
}

@Composable
private fun ProductSection(
    title: String,
    items: List<ProductItem>
) {
    Column {
        Text(
            text = title,
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(8.dp))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            items.forEach { item ->
                ProductCard(
                    item = item,
                    modifier = Modifier.weight(1f)
                )
            }
        }
    }
}

@Composable
private fun ProductCard(
    item: ProductItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(116.dp),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, Color.Gray),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Column {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(78.dp)
                    .background(Color(0xFFF6F6F6)),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = item.placeholder,
                    color = Color.Gray,
                    fontSize = 11.sp
                )

                // After adding images to res/drawable, use this:
                // Image(
                //     painter = painterResource(id = item.imageRes),
                //     contentDescription = item.name,
                //     modifier = Modifier.fillMaxSize().padding(6.dp),
                //     contentScale = ContentScale.Fit
                // )
            }

            Text(
                text = item.name,
                modifier = Modifier.padding(horizontal = 4.dp),
                fontSize = 8.sp,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )

            Text(
                text = item.price,
                modifier = Modifier.padding(horizontal = 4.dp),
                fontSize = 8.sp,
                maxLines = 1
            )
        }
    }
}

@Composable
private fun BottomNavBar() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        BottomNavItem("⌂", "Home", true)
        BottomNavItem("◉", "Explore", false)
        BottomNavItem("▾", "Orders", false)
        BottomNavItem("☏", "Chat", false)
        BottomNavItem("♡", "Account", false)
    }
}

@Composable
private fun RowScope.BottomNavItem(
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
                fontSize = 24.sp,
                color = Color.Black
            )
        },
        label = {
            Text(
                text = label,
                fontSize = 9.sp,
                color = Color.Black
            )
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent
        )
    )
}

private data class ProductItem(
    val name: String,
    val price: String,
    val placeholder: String
)

private val shoeItems = listOf(
    ProductItem("Nike SB Dunk Low Hein...", "₱335,800.00", "shoe 1"),
    ProductItem("7-Eleven x Nike SB Dunk...", "₱310,169.34", "shoe 2"),
    ProductItem("Nike Dunk High Coraline", "₱333,900.65", "shoe 3")
)

private val cardItems = listOf(
    ProductItem("Charizard-Holo 2016...", "₱143,671.00", "card 1"),
    ProductItem("Pokemon Fossil Rare...", "₱10,891.18", "card 2"),
    ProductItem("Solgaleo-GX - 155/149...", "₱3,954.86", "card 3")
)
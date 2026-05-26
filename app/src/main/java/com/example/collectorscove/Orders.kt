package com.example.collectorscove

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private enum class OrderTab(val title: String) {
    Ongoing("Ongoing"),
    Completed("Completed"),
    Bids("Bids"),
    Review("To Review")
}

@Composable
fun OrderScreen() {
    var selectedTab by remember { mutableStateOf(OrderTab.Ongoing) }

    Scaffold(
        bottomBar = { OrderBottomNavBar() },
        containerColor = Color.White
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            OrderTopBar()

            OrderTabs(
                selectedTab = selectedTab,
                onTabSelected = { selectedTab = it }
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 26.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp),
                contentPadding = PaddingValues(top = 14.dp, bottom = 24.dp)
            ) {
                items(5) { index ->
                    when (selectedTab) {
                        OrderTab.Ongoing -> OngoingOrderCard()
                        OrderTab.Completed -> CompletedOrderCard()
                        OrderTab.Bids -> BidOrderCard()
                        OrderTab.Review -> ReviewOrderCard(showReviewBox = index == 0)
                    }
                }
            }
        }
    }
}

@Composable
private fun OrderTopBar() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 26.dp, end = 26.dp, top = 30.dp, bottom = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "‹",
            fontSize = 44.sp,
            color = Color.Black
        )

        Text(
            text = "Order",
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = "Clear History",
            fontSize = 12.sp,
            color = Color.Gray
        )
    }
}

@Composable
private fun OrderTabs(
    selectedTab: OrderTab,
    onTabSelected: (OrderTab) -> Unit
) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderTab.entries.forEach { tab ->
                Text(
                    text = tab.title,
                    modifier = Modifier
                        .clickable { onTabSelected(tab) }
                        .padding(bottom = 10.dp),
                    fontSize = 12.sp,
                    color = if (selectedTab == tab) Color(0xFFB08C00) else Color.Gray
                )
            }
        }

        Divider(color = Color(0xFFE0E0E0), thickness = 1.dp)
    }
}

@Composable
private fun OngoingOrderCard() {
    OrderCardBase {
        ProductThumb()

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            Text(
                text = "Order Shipped",
                fontSize = 15.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Text(
                text = "Shipping ‘Charizard-Holo 2016 Pokemon TCG XY Evolutions #11/108 PSA 10 with product ID of #12345",
                fontSize = 11.sp,
                color = Color.Black,
                lineHeight = 12.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}

@Composable
private fun CompletedOrderCard() {
    OrderCardBase {
        ProductThumb()

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Charizard-Holo 2016 Pokemon TCG XY Evolutions #11/108 PSA 10",
                fontSize = 11.sp,
                color = Color.Black,
                lineHeight = 12.sp
            )
        }

        Column(
            horizontalAlignment = Alignment.End
        ) {
            Text(
                text = "Completed",
                fontSize = 11.sp,
                color = Color(0xFFB08C00)
            )

            Spacer(modifier = Modifier.height(22.dp))

            Text(
                text = "05/11/2025",
                fontSize = 11.sp,
                color = Color.Black
            )
        }
    }
}

@Composable
private fun BidOrderCard() {
    OrderCardBase {
        ProductThumb()

        Spacer(modifier = Modifier.width(14.dp))

        Column(
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = "Charizard-Holo 2016 Pokemon TCG XY Evolutions #11/108 PSA 10",
                fontSize = 11.sp,
                color = Color.Black,
                lineHeight = 12.sp
            )

            Spacer(modifier = Modifier.height(14.dp))

            Text(
                text = "Current Bid: ₱5,731.63",
                fontSize = 11.sp,
                color = Color.Black
            )
        }

        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Top Bidder",
                fontSize = 11.sp,
                color = Color(0xFFB08C00)
            )

            Text(
                text = "qxddimbee",
                fontSize = 10.sp,
                color = Color.Black
            )
        }

        Text(
            text = "⌄",
            modifier = Modifier.padding(start = 8.dp),
            fontSize = 18.sp,
            color = Color.LightGray
        )
    }
}

@Composable
private fun ReviewOrderCard(showReviewBox: Boolean) {
    Column {
        OrderCardBase {
            ProductThumb()

            Spacer(modifier = Modifier.width(14.dp))

            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(
                    text = "Charizard-Holo 2016 Pokemon TCG XY Evolutions #11/108 PSA 10",
                    fontSize = 11.sp,
                    color = Color.Black,
                    lineHeight = 12.sp
                )

                Spacer(modifier = Modifier.height(14.dp))

                Text(
                    text = "Total ₱5,731.63",
                    fontSize = 11.sp,
                    color = Color.Black
                )
            }

            Text(
                text = "Rate Seller",
                fontSize = 11.sp,
                color = Color(0xFFB08C00)
            )

            Text(
                text = "⌃",
                modifier = Modifier.padding(start = 8.dp),
                fontSize = 18.sp,
                color = Color.LightGray
            )
        }

        if (showReviewBox) {
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray)
                    .padding(20.dp)
            ) {
                Text(
                    text = "☆☆☆☆☆",
                    fontSize = 24.sp,
                    color = Color.Black
                )

                Spacer(modifier = Modifier.height(8.dp))

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(3.dp))
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text(
                        text = "Write your review here...",
                        fontSize = 10.sp,
                        color = Color.Gray
                    )
                }

                Spacer(modifier = Modifier.height(10.dp))

                Box(
                    modifier = Modifier
                        .align(Alignment.End)
                        .width(64.dp)
                        .height(20.dp)
                        .border(1.dp, Color(0xFFB08C00), RoundedCornerShape(4.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Submit",
                        fontSize = 9.sp,
                        color = Color.Black
                    )
                }
            }
        }
    }
}

@Composable
private fun OrderCardBase(
    content: @Composable RowScope.() -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .height(64.dp),
        shape = RoundedCornerShape(4.dp),
        border = BorderStroke(1.dp, Color(0xFFD6D6D6)),
        colors = CardDefaults.cardColors(containerColor = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            content = content
        )
    }
}

@Composable
private fun ProductThumb() {
    Box(
        modifier = Modifier
            .width(18.dp)
            .height(28.dp)
            .background(Color(0xFFEFEFEF))
            .border(1.dp, Color.LightGray),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "Card",
            fontSize = 5.sp,
            color = Color(0xFFB08C00)
        )

        // Replace with real card image later:
        // Image(
        //     painter = painterResource(id = R.drawable.charizard_card),
        //     contentDescription = "Charizard card",
        //     modifier = Modifier.fillMaxSize(),
        //     contentScale = ContentScale.Crop
        // )
    }
}

@Composable
private fun OrderBottomNavBar() {
    NavigationBar(
        containerColor = Color.White,
        tonalElevation = 0.dp
    ) {
        OrderBottomNavItem("⌂", "Home", false)
        OrderBottomNavItem("◉", "Explore", false)
        OrderBottomNavItem("▾", "Orders", true)
        OrderBottomNavItem("☏", "Chat", false)
        OrderBottomNavItem("♡", "Account", false)
    }
}

@Composable
private fun RowScope.OrderBottomNavItem(
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
                fontSize = 8.sp,
                color = Color.Black
            )
        },
        colors = NavigationBarItemDefaults.colors(
            indicatorColor = Color.Transparent
        )
    )
}
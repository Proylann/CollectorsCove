package com.example.collectorscove

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

private enum class OrderTab(val title: String) {
    Ongoing("Ongoing"),
    Completed("Completed"),
    Bids("Bids"),
    Review("To Review")
}

@Composable
fun OrderScreen() {
    var selectedTab by remember { mutableStateOf(OrderTab.Ongoing) }
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    AppMenuDrawer(drawerState = drawerState) {
        Scaffold(
            bottomBar = { OrderBottomNavBar() },
            containerColor = Color.White
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                OrderTopBar(onMenuClick = { scope.launch { drawerState.open() } })

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
}

@Composable
private fun OrderTopBar(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 26.dp, end = 26.dp, top = 30.dp, bottom = 28.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "☰",
            modifier = Modifier.clickable(onClick = onMenuClick),
            fontSize = 34.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.width(12.dp))

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
private fun OrderTabs(selectedTab: OrderTab, onTabSelected: (OrderTab) -> Unit) {
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

        HorizontalDivider(color = Color(0xFFE0E0E0), thickness = 1.dp)
    }
}

@Composable
private fun OngoingOrderCard() {
    OrderCardBase {
        ProductThumb()
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text("Order Shipped", fontSize = 15.sp, fontWeight = FontWeight.Bold)
            Text(
                text = "Shipping Charizard-Holo 2016 Pokemon TCG XY Evolutions #11/108 PSA 10 with product ID of #12345",
                fontSize = 11.sp,
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
        Text(
            text = "Charizard-Holo 2016 Pokemon TCG XY Evolutions #11/108 PSA 10",
            modifier = Modifier.weight(1f),
            fontSize = 11.sp,
            lineHeight = 12.sp
        )
        Column(horizontalAlignment = Alignment.End) {
            Text("Completed", fontSize = 11.sp, color = Color(0xFFB08C00))
            Spacer(modifier = Modifier.height(22.dp))
            Text("05/11/2025", fontSize = 11.sp)
        }
    }
}

@Composable
private fun BidOrderCard() {
    OrderCardBase {
        ProductThumb()
        Spacer(modifier = Modifier.width(14.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(
                text = "Charizard-Holo 2016 Pokemon TCG XY Evolutions #11/108 PSA 10",
                fontSize = 11.sp,
                lineHeight = 12.sp
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text("Current Bid: P5,731.63", fontSize = 11.sp)
        }
        Column {
            Text("Top Bidder", fontSize = 11.sp, color = Color(0xFFB08C00))
            Text("qxddimbee", fontSize = 10.sp)
        }
    }
}

@Composable
private fun ReviewOrderCard(showReviewBox: Boolean) {
    Column {
        OrderCardBase {
            ProductThumb()
            Spacer(modifier = Modifier.width(14.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = "Charizard-Holo 2016 Pokemon TCG XY Evolutions #11/108 PSA 10",
                    fontSize = 11.sp,
                    lineHeight = 12.sp
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text("Total P5,731.63", fontSize = 11.sp)
            }
            Text("Rate Seller", fontSize = 11.sp, color = Color(0xFFB08C00))
        }

        if (showReviewBox) {
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .border(1.dp, Color.LightGray)
                    .padding(20.dp)
            ) {
                Text("*****", fontSize = 24.sp)
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .border(1.dp, Color.Gray, RoundedCornerShape(3.dp))
                        .padding(horizontal = 8.dp),
                    contentAlignment = Alignment.CenterStart
                ) {
                    Text("Write your review here...", fontSize = 10.sp, color = Color.Gray)
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
                    Text("Submit", fontSize = 9.sp)
                }
            }
        }
    }
}

@Composable
private fun OrderCardBase(content: @Composable RowScope.() -> Unit) {
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
        Text("Card", fontSize = 5.sp, color = Color(0xFFB08C00))
    }
}

@Composable
private fun OrderBottomNavBar() {
    NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
        OrderBottomNavItem("Home", "Home", false)
        OrderBottomNavItem("Explore", "Explore", false)
        OrderBottomNavItem("Orders", "Orders", true)
        OrderBottomNavItem("Chat", "Chat", false)
        OrderBottomNavItem("Account", "Account", false)
    }
}

@Composable
private fun RowScope.OrderBottomNavItem(icon: String, label: String, selected: Boolean) {
    NavigationBarItem(
        selected = selected,
        onClick = {},
        icon = {
            Text(
                text = icon,
                fontSize = 10.sp,
                fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal,
                color = Color.Black
            )
        },
        label = { Text(text = label, fontSize = 8.sp, color = Color.Black) },
        colors = NavigationBarItemDefaults.colors(indicatorColor = Color.Transparent)
    )
}

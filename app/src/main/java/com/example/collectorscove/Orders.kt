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
import androidx.compose.material3.HorizontalDivider
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
import com.example.collectorscove.ui.theme.CoveBackground
import com.example.collectorscove.ui.theme.CoveBorder
import com.example.collectorscove.ui.theme.CoveGold
import com.example.collectorscove.ui.theme.CoveLightGray
import com.example.collectorscove.ui.theme.CoveSurface

private enum class OrderTab(val title: String) {
    Ongoing("Ongoing"),
    Completed("Completed"),
    Bids("Bids"),
    Review("To Review")
}

@Composable
fun OrderScreen(
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    var selectedTab by remember { mutableStateOf(OrderTab.Ongoing) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoveBackground)
    ) {
        Column(modifier = Modifier.padding(horizontal = 16.dp)) {
            Spacer(modifier = Modifier.height(24.dp))
            AppTopBar(
                onMenuClick = onMenuClick,
                onNotificationsClick = onNotificationsClick
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = "Orders",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        OrderTabs(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
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

@Composable
private fun OrderTabs(selectedTab: OrderTab, onTabSelected: (OrderTab) -> Unit) {
    Column {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            OrderTab.entries.forEach { tab ->
                Text(
                    text = tab.title,
                    modifier = Modifier
                        .clickable { onTabSelected(tab) }
                        .padding(bottom = 10.dp),
                    fontSize = 12.sp,
                    fontWeight = if (selectedTab == tab) FontWeight.Bold else FontWeight.Normal,
                    color = if (selectedTab == tab) CoveGold else Color.Gray
                )
            }
        }

        HorizontalDivider(color = CoveBorder, thickness = 1.dp)
    }
}

@Composable
private fun OngoingOrderCard() {
    OrderCardBase {
        ProductThumb()
        Spacer(modifier = Modifier.width(12.dp))
        Column {
            Text("Order Shipped", fontSize = 15.sp, fontWeight = FontWeight.Bold, color = Color.Black)
            Text(
                text = "Shipping Charizard-Holo 2016 Pokemon TCG XY Evolutions #11/108 PSA 10 with product ID of #12345",
                fontSize = 11.sp,
                lineHeight = 12.sp,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                color = Color.Black
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
            lineHeight = 12.sp,
            color = Color.Black
        )
        Column(horizontalAlignment = Alignment.End) {
            Text("Completed", fontSize = 11.sp, color = CoveGold, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(22.dp))
            Text("05/11/2025", fontSize = 11.sp, color = Color.Black)
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
                lineHeight = 12.sp,
                color = Color.Black
            )
            Spacer(modifier = Modifier.height(14.dp))
            Text("Current Bid: P5,731.63", fontSize = 11.sp, color = Color.Black)
        }
        Column {
            Text("Top Bidder", fontSize = 11.sp, color = CoveGold, fontWeight = FontWeight.Bold)
            Text("qxddimbee", fontSize = 10.sp, color = Color.Black)
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
                    lineHeight = 12.sp,
                    color = Color.Black
                )
                Spacer(modifier = Modifier.height(14.dp))
                Text("Total P5,731.63", fontSize = 11.sp, color = Color.Black)
            }
            Text("Rate Seller", fontSize = 11.sp, color = CoveGold, fontWeight = FontWeight.Bold)
        }

        if (showReviewBox) {
            Spacer(modifier = Modifier.height(12.dp))

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(CoveSurface, RoundedCornerShape(12.dp))
                    .border(1.dp, CoveBorder, RoundedCornerShape(12.dp))
                    .padding(20.dp)
            ) {
                Text("*****", fontSize = 24.sp, color = CoveGold)
                Spacer(modifier = Modifier.height(8.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(42.dp)
                        .border(1.dp, CoveBorder, RoundedCornerShape(8.dp))
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
                        .height(24.dp)
                        .background(CoveGold, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Submit", fontSize = 9.sp, color = Color.White, fontWeight = FontWeight.Bold)
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
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, CoveBorder),
        colors = CardDefaults.cardColors(containerColor = CoveSurface)
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
            .background(CoveLightGray)
            .border(1.dp, CoveBorder),
        contentAlignment = Alignment.Center
    ) {
        Text("Card", fontSize = 5.sp, color = CoveGold, fontWeight = FontWeight.Bold)
    }
}

package com.example.collectorscove

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
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
import com.example.collectorscove.ui.theme.CoveTextSecondary

@Composable
fun AppTopBar(
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
fun LogoMark() {
    Box(
        modifier = Modifier.width(100.dp),
        contentAlignment = Alignment.Center
    ) {
        Box(
            modifier = Modifier
                .size(30.dp)
                .align(Alignment.CenterStart)
                .background(CoveGold, CircleShape)
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
fun SectionHeader(
    title: String,
    actionText: String? = null,
    onActionClick: (() -> Unit)? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )
        if (actionText != null) {
            Text(
                text = actionText,
                fontSize = 12.sp,
                color = Color.Gray,
                modifier = if (onActionClick != null) {
                    Modifier.clickable(onClick = onActionClick)
                } else {
                    Modifier
                }
            )
        }
    }
}

@Composable
fun ProductGrid(
    items: List<CollectibleItem>,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        items.chunked(2).forEach { rowItems ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                rowItems.forEach { item ->
                    CollectibleProductCard(
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
fun CollectibleProductCard(
    item: CollectibleItem,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier.height(178.dp),
        shape = RoundedCornerShape(12.dp),
        border = BorderStroke(1.dp, CoveBorder),
        colors = CardDefaults.cardColors(containerColor = CoveSurface)
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(104.dp)
                    .background(item.imageColor),
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
                    color = CoveGold,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

data class CollectibleItem(
    val imageLabel: String,
    val name: String,
    val price: String,
    val imageColor: Color
)

package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.collectorscove.data.model.CollectibleItem
import com.example.collectorscove.ui.theme.CoveBorder
import com.example.collectorscove.ui.theme.CoveGold
import com.example.collectorscove.ui.theme.CoveLightGray
import com.example.collectorscove.ui.theme.CoveSurface
import java.util.Locale

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
fun ItemCard(item: CollectibleItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        shape = RoundedCornerShape(12.dp),
        colors = CardDefaults.cardColors(containerColor = CoveSurface)
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color.LightGray, RoundedCornerShape(8.dp)),
                contentAlignment = Alignment.Center
            ) {
                if (item.imageUrl.isNotEmpty()) {
                    AsyncImage(
                        model = item.imageUrl,
                        contentDescription = item.name,
                        modifier = Modifier.fillMaxSize(),
                        contentScale = ContentScale.Crop,
                        error = androidx.compose.ui.graphics.painter.ColorPainter(Color.LightGray),
                        placeholder = androidx.compose.ui.graphics.painter.ColorPainter(Color.LightGray)
                    )
                } else {
                    Text(item.category, color = Color.DarkGray)
                }
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = item.name,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                maxLines = 1
            )
            Text(
                text = "P${String.format(Locale.getDefault(), "%,.2f", item.price)}",
                color = CoveGold,
                fontSize = 13.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}

@Composable
fun ItemDetailDialog(
    item: CollectibleItem,
    currentUserUid: String?,
    onDismiss: () -> Unit,
    onOrder: () -> Unit
) {
    var isOrdering by remember { mutableStateOf(false) }

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            if (currentUserUid != null && currentUserUid != item.sellerId) {
                Button(
                    onClick = {
                        isOrdering = true
                        onOrder()
                    },
                    enabled = !isOrdering
                ) {
                    if (isOrdering) {
                        CircularProgressIndicator(
                            modifier = Modifier.size(20.dp),
                            color = Color.White,
                            strokeWidth = 2.dp
                        )
                    } else {
                        Text("Order Now")
                    }
                }
            } else if (currentUserUid == item.sellerId) {
                Text(
                    text = "You own this item",
                    modifier = Modifier.padding(8.dp),
                    color = Color.Gray,
                    fontSize = 12.sp
                )
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss, enabled = !isOrdering) {
                Text("Cancel")
            }
        },
        title = { Text(item.name) },
        text = {
            Column {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .background(Color.LightGray, RoundedCornerShape(8.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    if (item.imageUrl.isNotEmpty()) {
                        AsyncImage(
                            model = item.imageUrl,
                            contentDescription = item.name,
                            modifier = Modifier.fillMaxSize(),
                            contentScale = ContentScale.Crop,
                            error = androidx.compose.ui.graphics.painter.ColorPainter(Color.LightGray),
                            placeholder = androidx.compose.ui.graphics.painter.ColorPainter(Color.LightGray)
                        )
                    } else {
                        Text("No Image", color = Color.DarkGray)
                    }
                }
                Spacer(modifier = Modifier.height(8.dp))
                Text("Category: ${item.category}", fontWeight = FontWeight.SemiBold)
                Text(
                    text = "Price: P${String.format(Locale.getDefault(), "%,.2f", item.price)}",
                    color = CoveGold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text(item.description)
            }
        }
    )
}

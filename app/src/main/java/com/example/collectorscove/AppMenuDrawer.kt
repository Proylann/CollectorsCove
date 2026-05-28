package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun AppMenuDrawer(
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            ModalDrawerSheet(
                modifier = Modifier
                    .width(260.dp)
                    .fillMaxHeight(),
                drawerContainerColor = Color.White
            ) {
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp, vertical = 28.dp)
                ) {
                    Text(
                        text = "Collectors Cove",
                        fontSize = 22.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )

                    Spacer(Modifier.height(24.dp))

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Text(
                            text = "JL",
                            modifier = Modifier
                                .size(46.dp)
                                .background(Color(0xFFE0E0E0), CircleShape)
                                .padding(top = 13.dp),
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black
                        )

                        Spacer(Modifier.width(12.dp))

                        Column {
                            Text("John Lennon", fontSize = 15.sp, fontWeight = FontWeight.Bold)
                            Text("View Profile", fontSize = 12.sp, color = Color.Gray)
                        }
                    }

                    Spacer(Modifier.height(28.dp))

                    listOf(
                        "My Collection",
                        "Wishlist",
                        "Sell Item",
                        "Notifications",
                        "Settings",
                        "Help Center"
                    ).forEach { item ->
                        DrawerMenuItem(
                            label = item,
                            onClick = { scope.launch { drawerState.close() } }
                        )
                    }

                    Spacer(Modifier.weight(1f))

                    DrawerMenuItem(
                        label = "Log out",
                        isDestructive = true,
                        onClick = { scope.launch { drawerState.close() } }
                    )
                }
            }
        },
        content = content
    )
}

@Composable
private fun DrawerMenuItem(
    label: String,
    isDestructive: Boolean = false,
    onClick: () -> Unit
) {
    Text(
        text = label,
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(vertical = 14.dp),
        fontSize = 15.sp,
        fontWeight = FontWeight.Medium,
        color = if (isDestructive) Color.Red else Color.Black
    )
}

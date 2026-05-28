package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

@Composable
fun NotificationScreen() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    AppMenuDrawer(drawerState = drawerState) {
        Scaffold(
            bottomBar = { NotificationBottomNavBar() },
            containerColor = Color.White
        ) { padding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = 16.dp)
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                NotificationTopBar(onMenuClick = { scope.launch { drawerState.open() } })
                Spacer(modifier = Modifier.height(18.dp))

                Text("Notifications", fontSize = 20.sp, fontWeight = FontWeight.Bold)

                Spacer(modifier = Modifier.height(18.dp))

                Text("A minute ago", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                NotificationCard(
                    beforeLink = "Bid is closing in 2 minutes! ",
                    link = "View Bid on Nike Air ..."
                )

                Spacer(modifier = Modifier.height(16.dp))

                Text("Today", fontSize = 14.sp, fontWeight = FontWeight.Bold)
                Spacer(modifier = Modifier.height(8.dp))
                NotificationCard(
                    beforeLink = "Look! a new post from Danny. ",
                    link = "View Post"
                )

                Spacer(modifier = Modifier.height(48.dp))

                Box(
                    modifier = Modifier
                        .align(Alignment.CenterHorizontally)
                        .size(48.dp)
                        .background(Color(0xFF03A9F4), CircleShape)
                        .padding(6.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxSize()
                            .background(Color.White, CircleShape),
                        contentAlignment = Alignment.Center
                    ) {
                        Text("Bell", fontSize = 10.sp)
                    }
                }
            }
        }
    }
}

@Composable
private fun NotificationTopBar(onMenuClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "☰",
            modifier = Modifier.clickable(onClick = onMenuClick),
            fontSize = 26.sp,
            color = Color.Black
        )

        Spacer(modifier = Modifier.weight(1f))

        Box(
            modifier = Modifier.width(92.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .align(Alignment.CenterStart)
                    .background(Color(0xFFBFA619), CircleShape)
            )

            Column(modifier = Modifier.padding(start = 20.dp)) {
                Text("Collectors", fontSize = 12.sp, fontWeight = FontWeight.Bold, lineHeight = 10.sp)
                Text("Cove", fontSize = 12.sp, fontWeight = FontWeight.Bold, lineHeight = 10.sp)
                Text("est. 2024", fontSize = 4.sp)
            }
        }

        Spacer(modifier = Modifier.weight(1f))

        Text("Search", fontSize = 10.sp, color = Color.Black)
        Spacer(modifier = Modifier.width(16.dp))
        Text("Bell", fontSize = 10.sp, color = Color.Black)
    }
}

@Composable
private fun NotificationCard(beforeLink: String, link: String) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(34.dp)
            .border(1.dp, Color.Gray, RoundedCornerShape(2.dp))
            .padding(horizontal = 12.dp),
        contentAlignment = Alignment.CenterStart
    ) {
        Text(
            text = buildAnnotatedString {
                append(beforeLink)
                withStyle(
                    SpanStyle(
                        color = Color(0xFF8A6F00),
                        textDecoration = TextDecoration.Underline
                    )
                ) {
                    append(link)
                }
            },
            fontSize = 11.sp,
            color = Color.Black,
            maxLines = 1
        )
    }
}

@Composable
private fun NotificationBottomNavBar() {
    NavigationBar(containerColor = Color.White, tonalElevation = 0.dp) {
        NotificationBottomItem("Home", "Home", true)
        NotificationBottomItem("Explore", "Explore", false)
        NotificationBottomItem("Orders", "Orders", false)
        NotificationBottomItem("Chat", "Chat", false)
        NotificationBottomItem("Account", "Account", false)
    }
}

@Composable
private fun RowScope.NotificationBottomItem(icon: String, label: String, selected: Boolean) {
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

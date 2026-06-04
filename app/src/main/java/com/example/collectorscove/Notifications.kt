package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectorscove.ui.theme.CoveBackground
import com.example.collectorscove.ui.theme.CoveBorder
import com.example.collectorscove.ui.theme.CoveGold
import com.example.collectorscove.ui.theme.CoveSurface

@Composable
fun NotificationScreen(
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(CoveBackground)
            .verticalScroll(rememberScrollState())
            .padding(horizontal = 16.dp)
    ) {
        Spacer(modifier = Modifier.height(24.dp))
        AppTopBar(
            onMenuClick = onMenuClick,
            onNotificationsClick = onNotificationsClick
        )
        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Notifications",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black
        )

        Spacer(modifier = Modifier.height(18.dp))

        Text("A minute ago", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        NotificationCard(
            beforeLink = "Bid is closing in 2 minutes! ",
            link = "View Bid on Nike Air ..."
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text("Today", fontSize = 14.sp, fontWeight = FontWeight.Bold, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))
        NotificationCard(
            beforeLink = "Look! a new post from Danny. ",
            link = "View Post"
        )

        Spacer(modifier = Modifier.height(24.dp))
    }
}

@Composable
private fun NotificationCard(beforeLink: String, link: String) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(CoveSurface, RoundedCornerShape(12.dp))
            .border(1.dp, CoveBorder, RoundedCornerShape(12.dp))
            .padding(horizontal = 12.dp, vertical = 10.dp)
    ) {
        Text(
            text = buildAnnotatedString {
                append(beforeLink)
                withStyle(
                    SpanStyle(
                        color = CoveGold,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.SemiBold
                    )
                ) {
                    append(link)
                }
            },
            fontSize = 12.sp,
            color = Color.Black,
            lineHeight = 16.sp
        )
    }
}

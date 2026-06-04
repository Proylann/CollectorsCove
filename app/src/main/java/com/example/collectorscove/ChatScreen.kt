package com.example.collectorscove

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.collectorscove.ui.theme.CoveBackground
import com.example.collectorscove.ui.theme.CoveBorder
import com.example.collectorscove.ui.theme.CoveGold
import com.example.collectorscove.ui.theme.CoveLightGray
import com.example.collectorscove.ui.theme.CoveSurface

private data class ChatMessage(
    val text: String,
    val fromUser: Boolean
)

@Composable
fun ChatScreen(
    onMenuClick: () -> Unit,
    onNotificationsClick: () -> Unit
) {
    val messages = remember {
        mutableStateListOf(
            ChatMessage(
                text = "Congratulations! Party\n\nWe're thrilled to inform you that you have successfully won the bidding for Pokemon Fossil Rare Holo Dragonite #4",
                fromUser = false
            ),
            ChatMessage(
                text = "Your item is on the way!",
                fromUser = false
            ),
            ChatMessage(
                text = "thanks so much!",
                fromUser = true
            )
        )
    }
    var typedMessage by remember { mutableStateOf("") }

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
            Spacer(modifier = Modifier.height(12.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "123 Store",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Spacer(Modifier.weight(1f))
                Text(
                    text = "...",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold,
                    color = CoveGold
                )
            }
        }

        HorizontalDivider(
            modifier = Modifier.padding(top = 12.dp),
            color = CoveBorder
        )

        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            item { Spacer(Modifier.height(12.dp)) }
            items(messages) { message ->
                ChatBubbleRow(message)
                Spacer(Modifier.height(12.dp))
            }
        }

        ChatInputBar(
            value = typedMessage,
            onValueChange = { typedMessage = it },
            onSend = {
                if (typedMessage.isNotBlank()) {
                    messages.add(ChatMessage(typedMessage.trim(), fromUser = true))
                    typedMessage = ""
                }
            }
        )
    }
}

@Composable
private fun ChatBubbleRow(message: ChatMessage) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (message.fromUser) Arrangement.End else Arrangement.Start,
        verticalAlignment = Alignment.Bottom
    ) {
        if (!message.fromUser) {
            AvatarDot()
            Spacer(Modifier.width(10.dp))
        }

        Box(
            modifier = Modifier
                .width(if (message.text.length > 40) 192.dp else 122.dp)
                .clip(RoundedCornerShape(12.dp))
                .background(if (message.fromUser) CoveGold.copy(alpha = 0.15f) else CoveLightGray)
                .border(1.dp, CoveBorder, RoundedCornerShape(12.dp))
                .padding(horizontal = 8.dp, vertical = 8.dp)
        ) {
            Text(
                text = message.text,
                fontSize = 12.sp,
                lineHeight = 15.sp,
                color = Color.Black
            )
        }

        if (message.fromUser) {
            Spacer(Modifier.width(10.dp))
            AvatarDot()
        }
    }
}

@Composable
private fun AvatarDot() {
    Box(
        modifier = Modifier
            .size(20.dp)
            .background(CoveGold.copy(alpha = 0.3f), CircleShape)
            .border(1.dp, CoveGold, CircleShape)
    )
}

@Composable
private fun ChatInputBar(
    value: String,
    onValueChange: (String) -> Unit,
    onSend: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(CoveGold)
            .imePadding()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            placeholder = {
                Text(
                    text = "Message...",
                    fontSize = 12.sp,
                    color = Color.Gray
                )
            },
            modifier = Modifier
                .weight(1f)
                .height(40.dp),
            singleLine = true,
            shape = RoundedCornerShape(8.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = CoveSurface,
                unfocusedContainerColor = CoveSurface,
                focusedBorderColor = CoveSurface,
                unfocusedBorderColor = CoveSurface,
                cursorColor = Color.Black
            )
        )

        Spacer(Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .clickable(onClick = onSend),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = ">",
                modifier = Modifier.padding(bottom = 2.dp),
                fontSize = 34.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
}

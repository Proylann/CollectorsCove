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
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch

private data class ChatMessage(
    val text: String,
    val fromUser: Boolean
)

@Composable
fun ChatScreen() {
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

    Scaffold(
        topBar = { ChatTopBar(onMenuClick = { /* Handled globally */ }) },
        bottomBar = {
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
        },
        containerColor = Color.White
    ) { padding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 14.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            items(messages) { message ->
                ChatBubbleRow(message)
                Spacer(Modifier.height(12.dp))
            }
        }
    }
}

@Composable
private fun ChatTopBar(onMenuClick: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 10.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "☰",
                modifier = Modifier.clickable(onClick = onMenuClick),
                fontSize = 22.sp,
                color = Color.Black
            )

            Spacer(Modifier.width(14.dp))

            Text(
                text = "123 Store",
                fontSize = 13.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )

            Spacer(Modifier.weight(1f))

            Text(
                text = "...",
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFB8970A)
            )
        }

        HorizontalDivider(color = Color(0xFFE0E0E0))
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
                .clip(RoundedCornerShape(4.dp))
                .background(Color(0xFFE9EAED))
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
            .background(Color(0xFFD9D9D9), CircleShape)
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
            .background(Color(0xFFB89D08))
            .imePadding()
            .padding(horizontal = 22.dp, vertical = 8.dp),
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
            shape = RoundedCornerShape(4.dp),
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = Color.White,
                unfocusedContainerColor = Color.White,
                focusedBorderColor = Color.White,
                unfocusedBorderColor = Color.White,
                cursorColor = Color.Black
            )
        )

        Spacer(Modifier.width(12.dp))

        Box(
            modifier = Modifier
                .size(34.dp)
                .clip(CircleShape)
                .border(0.dp, Color.Transparent, CircleShape)
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

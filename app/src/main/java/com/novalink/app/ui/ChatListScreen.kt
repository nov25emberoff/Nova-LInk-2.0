package com.novalink.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.novalink.app.data.Chat
import com.novalink.app.ui.theme.NovaOrange
import com.novalink.app.ui.theme.NovaSurfaceVariant
import com.novalink.app.viewmodel.ChatViewModel
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatListScreen(
    viewModel: ChatViewModel,
    onChatClick: (Long, String) -> Unit
) {
    val chats by viewModel.chats.collectAsState(initial = emptyList())
    var showDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Nova Link", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color.Black,
                    titleContentColor = NovaOrange
                )
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { showDialog = true },
                containerColor = NovaOrange,
                contentColor = Color.Black
            ) {
                Icon(Icons.Filled.Add, contentDescription = "Новый чат")
            }
        },
        containerColor = Color.Black
    ) { padding ->
        if (chats.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxSize().padding(padding),
                contentAlignment = Alignment.Center
            ) {
                Text("Нет чатов. Нажмите +, чтобы начать", color = Color.Gray)
            }
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize().padding(padding)) {
                items(chats, key = { it.id }) { chat ->
                    ChatRow(chat = chat, onClick = { onChatClick(chat.id, chat.name) })
                    HorizontalDivider(color = NovaSurfaceVariant, thickness = 1.dp)
                }
            }
        }
    }

    if (showDialog) {
        NewChatDialog(
            onDismiss = { showDialog = false },
            onConfirm = { name ->
                viewModel.createChat(name)
                showDialog = false
            }
        )
    }
}

@Composable
fun ChatRow(chat: Chat, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 12.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Box(
            modifier = Modifier
                .size(48.dp)
                .clip(CircleShape)
                .background(NovaOrange),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = chat.name.take(1).uppercase(),
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
        Spacer(modifier = Modifier.width(12.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(chat.name, color = Color.White, fontWeight = FontWeight.SemiBold)
            if (chat.lastMessage.isNotBlank()) {
                Text(
                    chat.lastMessage,
                    color = Color.Gray,
                    maxLines = 1
                )
            }
        }
        if (chat.lastTimestamp > 0) {
            Text(
                text = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(chat.lastTimestamp)),
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
    }
}

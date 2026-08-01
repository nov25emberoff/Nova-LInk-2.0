package com.novalink.app.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.novalink.app.ui.theme.NovaBubbleOther
import com.novalink.app.ui.theme.NovaOrange
import com.novalink.app.viewmodel.ChatViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(
    viewModel: ChatViewModel,
    chatId: Long,
    chatName: String,
    onBack: () -> Unit
) {
    val messages by viewModel.messagesFor(chatId).collectAsState(initial = emptyList())
    var input by remember { mutableStateOf("") }
    val listState = rememberLazyListState()
    val scope = rememberCoroutineScope()

    LaunchedEffect(messages.size) {
        if (messages.isNotEmpty()) {
            scope.launch { listState.animateScrollToItem(messages.size - 1) }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(chatName, fontWeight = FontWeight.Bold, color = Color.White) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Назад", tint = NovaOrange)
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(containerColor = Color.Black)
            )
        },
        containerColor = Color.Black,
        bottomBar = {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Black)
                    .padding(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                OutlinedTextField(
                    value = input,
                    onValueChange = { input = it },
                    modifier = Modifier.weight(1f),
                    placeholder = { Text("Сообщение") },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedBorderColor = NovaOrange,
                        unfocusedBorderColor = Color.Gray,
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color.White,
                        cursorColor = NovaOrange
                    )
                )
                Spacer(modifier = Modifier.width(8.dp))
                IconButton(
                    onClick = {
                        if (input.isNotBlank()) {
                            viewModel.sendMessage(chatId, input)
                            input = ""
                        }
                    }
                ) {
                    Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Отправить", tint = NovaOrange)
                }
            }
        }
    ) { padding ->
        LazyColumn(
            state = listState,
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 8.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            items(messages) { message ->
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = if (message.isMine) Arrangement.End else Arrangement.Start
                ) {
                    Box(
                        modifier = Modifier
                            .clip(RoundedCornerShape(14.dp))
                            .background(if (message.isMine) NovaOrange else NovaBubbleOther)
                            .widthIn(max = 260.dp)
                            .padding(horizontal = 12.dp, vertical = 8.dp)
                    ) {
                        Column {
                            Text(
                                message.text,
                                color = if (message.isMine) Color.Black else Color.White
                            )
                            Text(
                                SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(message.timestamp)),
                                color = if (message.isMine) Color(0xFF3D2200) else Color.Gray,
                                style = MaterialTheme.typography.labelSmall
                            )
                        }
                    }
                }
            }
        }
    }
}

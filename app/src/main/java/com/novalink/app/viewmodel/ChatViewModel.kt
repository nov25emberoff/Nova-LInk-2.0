package com.novalink.app.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.novalink.app.data.Chat
import com.novalink.app.data.Message
import com.novalink.app.data.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class ChatViewModel(private val repository: Repository) : ViewModel() {

    val chats: Flow<List<Chat>> = repository.chats()

    fun messagesFor(chatId: Long): Flow<List<Message>> = repository.messages(chatId)

    fun createChat(name: String) {
        if (name.isBlank()) return
        viewModelScope.launch {
            repository.createChat(name.trim())
        }
    }

    fun sendMessage(chatId: Long, text: String) {
        if (text.isBlank()) return
        viewModelScope.launch {
            repository.sendMessage(chatId, text.trim(), isMine = true)
        }
    }
}

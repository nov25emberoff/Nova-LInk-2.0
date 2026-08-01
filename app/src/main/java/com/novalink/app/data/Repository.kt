package com.novalink.app.data

import kotlinx.coroutines.flow.Flow

class Repository(private val db: AppDatabase) {

    fun chats(): Flow<List<Chat>> = db.chatDao().getAll()

    fun messages(chatId: Long): Flow<List<Message>> = db.messageDao().getForChat(chatId)

    suspend fun createChat(name: String): Long {
        return db.chatDao().insert(Chat(name = name))
    }

    suspend fun sendMessage(chatId: Long, text: String, isMine: Boolean = true) {
        db.messageDao().insert(Message(chatId = chatId, text = text, isMine = isMine))
        val chat = db.chatDao().getById(chatId)
        if (chat != null) {
            db.chatDao().update(chat.copy(lastMessage = text, lastTimestamp = System.currentTimeMillis()))
        }
    }
}

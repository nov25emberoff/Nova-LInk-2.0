package com.novalink.app.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MessageDao {
    @Query("SELECT * FROM messages WHERE chatId = :chatId ORDER BY timestamp ASC")
    fun getForChat(chatId: Long): Flow<List<Message>>

    @Insert
    suspend fun insert(message: Message): Long
}

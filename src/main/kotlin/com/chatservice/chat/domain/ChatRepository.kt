package com.chatservice.chat.domain

import com.chatservice.chat.entity.ChatEntity

interface ChatRepository {
    fun findAllByThreadId(id: Long): List<ChatEntity>
    fun save(chat: ChatEntity): ChatEntity
    fun findAllByThreadIdIn(ids: List<Long>): List<ChatEntity>
}

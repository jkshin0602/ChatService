package com.chatservice.chat.domain

import com.chatservice.chat.entity.ThreadEntity

interface ThreadRepository {
    fun findFirstByUserIdOrderByLastMessageAtDesc(userId: Long): ThreadEntity?
    fun save(thread: ThreadEntity): ThreadEntity
    fun findByIdOrNull(threadId: Long): ThreadEntity?
    fun deleteById(threadId: Long)
}

package com.chatservice.chat.domain

import com.chatservice.chat.entity.ThreadEntity

interface ThreadRepository {
    fun findFirstByUserIdOrderByLastMessageAtDesc(userId: Long): ThreadEntity?
    fun save(thread: ThreadEntity): ThreadEntity
}

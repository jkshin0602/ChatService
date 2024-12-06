package com.chatservice.chat.entity

import com.chatservice.common.jpa_audit.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table
import java.time.LocalDateTime

@Table(name = "threads")
@Entity
class ThreadEntity(
    @Column(name = "user_id", nullable = false)
    val userId: Long,
) : BaseTimeEntity() {
    fun isExpired(): Boolean {
        return lastModifiedDate?.let { lastMessageAt ->
            LocalDateTime.now() > lastMessageAt.plusMinutes(THREAD_TIMEOUT_MINUTES)
        } ?: false
    }

    companion object {
        private const val THREAD_TIMEOUT_MINUTES = 30L

        fun create(userId: Long) = ThreadEntity(userId)
    }
}

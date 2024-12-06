package com.chatservice.chat.entity

import com.chatservice.common.jpa_audit.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Table

@Table(name = "chats")
@Entity
class ChatEntity(
    @Column(name = "thread_id", nullable = false)
    val threadId: Long,
    @Column(name = "question", columnDefinition = "TEXT")
    val question: String,
    @Column(name = "answer", columnDefinition = "TEXT")
    val answer: String,
) : BaseTimeEntity() {
    companion object {
        fun create(
            threadId: Long,
            question: String,
            answer: String,
        ) = ChatEntity(
            threadId = threadId,
            question = question,
            answer = answer,
        )
    }

}

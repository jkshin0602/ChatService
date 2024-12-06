package com.chatservice.chat.domain

import com.chatservice.chat.entity.ChatEntity
import java.time.LocalDateTime

data class ThreadDomain(
    val userId: Long,
    val threadId: Long,
    val chats: List<ChatDomain>,
) {
    companion object {
        fun create(
            userId: Long,
            threadId: Long,
            chats: List<ChatEntity>,
        ): ThreadDomain {
           return ThreadDomain(
                userId = userId,
                threadId = threadId,
                chats = chats.flatMap {
                    listOf(
                        ChatDomain(
                            chatId = it.id!!,
                            createDateTime = it.createdDate!!,
                            message = it.question,
                            role = OpenAIRole.USER,
                        ),
                        ChatDomain(
                            chatId = it.id!!,
                            createDateTime = it.createdDate!!,
                            message = it.answer,
                            role = OpenAIRole.SYSTEM,
                        )
                    )
                },
            )
        }
    }
}

data class ChatDomain(
    val chatId: Long,
    val createDateTime: LocalDateTime,
    val message: String,
    val role: OpenAIRole,
)

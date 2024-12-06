package com.chatservice.chat.domain

import com.chatservice.chat.entity.ChatEntity
import com.chatservice.chat.entity.ThreadEntity
import com.chatservice.user.domain.UserManager
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class ChatManager(
    private val threadRepository: ThreadRepository,
    private val chatRepository: ChatRepository,
    private val openApiManager: OpenApiManager,
    private val userManager: UserManager,
) {
    fun sendMessage(request: ChatRequest): ChatResponse {
        validate(request)

        val thread = getOrCreateThread(request.userId)
        val chats = chatRepository.findAllByThreadId(thread.id!!)

        val openApiRequest = createOpenApiRequest(chats, request.model)

        val result = openApiManager.sendMessage(openApiRequest)

        val chatEntity = ChatEntity.create(
            threadId = thread.id!!,
            question = request.question,
            answer = result.answer,
        ).also { chatRepository.save(it) }

        return ChatResponse(
            question = chatEntity.question,
            answer = chatEntity.answer,
            createdAt = LocalDateTime.now(),
        )
    }

    private fun createOpenApiRequest(
        chats: List<ChatEntity>,
        model: OpenAIModel,
    ): OpenApiRequest {
        val messages = chats.sortedBy { it.createdDate }.flatMap {
            listOf(
                Chats(
                    role = OpenAIRole.USER,
                    content = it.question,
                ),
                Chats(
                    role = OpenAIRole.SYSTEM,
                    content = it.answer,
                ),
            )
        }
        return OpenApiRequest(messages, model)
    }

    private fun getOrCreateThread(userId: Long): ThreadEntity {
        val lastThread = threadRepository.findFirstByUserIdOrderByLastMessageAtDesc(userId)

        return when {
            lastThread == null || lastThread.isExpired() -> createNewThread(userId)
            else -> lastThread
        }
    }

    private fun createNewThread(userId: Long): ThreadEntity {
        return ThreadEntity.create(userId).also { threadRepository.save(it) }
    }

    private fun validate(request: ChatRequest) {
        require(userManager.existUser(request.userId)) { "Nonexistent user" }
        require(request.question.isNotBlank()) { "question must not be blank" }
    }
}

data class ChatRequest(
    val question: String,
    val isStreaming: Boolean = false,
    val model: OpenAIModel = OpenAIModel.GPT_3_5_TURBO,
    val userId: Long,
)

data class ChatResponse(
    val question: String,
    val answer: String,
    val createdAt: LocalDateTime,
)

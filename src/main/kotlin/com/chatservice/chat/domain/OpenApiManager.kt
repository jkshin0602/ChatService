package com.chatservice.chat.domain

import org.springframework.stereotype.Component

interface OpenApiManager {
    fun sendMessage(request: OpenApiRequest): OpenApiResponse
}

@Component
class DefaultOpenApiManager : OpenApiManager {
    override fun sendMessage(request: OpenApiRequest): OpenApiResponse {
        return OpenApiResponse("mock answer")
    }
}

data class OpenApiResponse(
    val answer: String,
)

data class OpenApiRequest(
    val messages: List<Chats>,
    val model: OpenAIModel = OpenAIModel.GPT_3_5_TURBO,
)

data class Chats(
    val role: OpenAIRole,
    val content: String,
)

enum class OpenAIRole(
    val description: String,
) {
    USER("user"),
    SYSTEM("system"),
    ASSISTANT("assistant"),
    ;
}

enum class OpenAIModel(
    val description: String,
){
    GPT_3_5_TURBO("gpt-3.5-turbo"),
    ;
}

package com.chatservice.user_api.dto.request

data class JoinUserApiRequest(
    val name: String,
    val password: String,
    val email: String,
)

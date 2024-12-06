package com.chatservice.user_api.dto.request

data class LoginUserApiRequest(
    val email: String,
    val password: String,
)

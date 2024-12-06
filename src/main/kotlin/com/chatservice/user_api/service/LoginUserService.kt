package com.chatservice.user_api.service

import com.chatservice.user.domain.LoginProcessor
import com.chatservice.user.domain.LoginRequest
import com.chatservice.user_api.dto.request.LoginUserApiRequest
import com.chatservice.user_api.dto.response.LoginUserApiResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class LoginUserService(
    private val loginProcessor: LoginProcessor,
) {
    fun login(request: LoginUserApiRequest): LoginUserApiResponse {
        val token = loginProcessor.login(request.toRequest())
        return LoginUserApiResponse(token.value)
    }

    private fun LoginUserApiRequest.toRequest() = LoginRequest(
        email = email,
        password = password,
    )
}

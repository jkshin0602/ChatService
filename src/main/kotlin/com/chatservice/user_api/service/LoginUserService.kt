package com.chatservice.user_api.service

import com.chatservice.events.ActivityType
import com.chatservice.events.UserActivityEventDispatcher
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
    private val userActivityEventDispatcher: UserActivityEventDispatcher,
) {
    fun login(request: LoginUserApiRequest): LoginUserApiResponse {
        val token = loginProcessor.login(request.toRequest())
        return LoginUserApiResponse(token.value).also {
            userActivityEventDispatcher.publish(ActivityType.LOGIN)
        }
    }

    private fun LoginUserApiRequest.toRequest() = LoginRequest(
        email = email,
        password = password,
    )
}

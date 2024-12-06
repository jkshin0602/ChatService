package com.chatservice.user_api.service

import com.chatservice.events.ActivityType
import com.chatservice.events.UserActivityEventDispatcher
import com.chatservice.user.domain.JoinUserProcessor
import com.chatservice.user.domain.JoinUserRequest
import com.chatservice.user_api.dto.request.JoinUserApiRequest
import com.chatservice.user_api.dto.response.JoinUserApiResponse
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Transactional
@Service
class JoinUserService(
    private val joinUserProcessor: JoinUserProcessor,
    private val userActivityEventDispatcher: UserActivityEventDispatcher,
) {
    fun join(request: JoinUserApiRequest): JoinUserApiResponse {
        val result = joinUserProcessor.join(request.toDto())

        return JoinUserApiResponse(
            id = result.id,
            email = result.email,
        ).also {
            userActivityEventDispatcher.publish(ActivityType.JOIN)
        }
    }

    private fun JoinUserApiRequest.toDto() = JoinUserRequest(
        name = name,
        password = password,
        email = email,
    )
}

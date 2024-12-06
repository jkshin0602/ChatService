package com.chatservice.user_api.service

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
) {
    fun join(request: JoinUserApiRequest): JoinUserApiResponse {
        val result = joinUserProcessor.join(request.toDto())

        return JoinUserApiResponse(
            id = result.id,
            email = result.email,
        )
    }

    private fun JoinUserApiRequest.toDto() = JoinUserRequest(
        name = name,
        password = password,
        email = email,
    )
}

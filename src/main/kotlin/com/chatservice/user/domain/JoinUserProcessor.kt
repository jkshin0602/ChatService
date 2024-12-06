package com.chatservice.user.domain

import com.chatservice.user.entity.UserEntity
import org.springframework.stereotype.Component

@Component
class JoinUserProcessor(
    private val userRepository: UserRepository,
) {
    fun join(request: JoinUserRequest): JoinUserResponse {
        validate(request)

        val user = UserEntity.create(
            name = request.name,
            password = request.password,
            email = request.email,
        ).apply { userRepository.save(this) }

        return JoinUserResponse(
            id = user.id!!,
            email = user.email,
        )
    }

    private fun validate(request: JoinUserRequest) {
        require(request.name.isNotBlank()) { "Name cannot be blank" }
        require(request.email.isNotBlank()) { "Email cannot be blank" }
        require(request.password.isNotBlank()) { "Password cannot be blank" }
    }
}

data class JoinUserRequest(
    val name: String,
    val password: String,
    val email: String,
)

data class JoinUserResponse(
    val id: Long,
    val email: String,
)

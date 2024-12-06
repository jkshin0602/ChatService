package com.chatservice.user.domain

import com.chatservice.user.entity.UserEntity
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class JoinUserProcessor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
) {
    fun join(request: JoinUserRequest): JoinUserResponse {
        validateRequest(request)
        require(userRepository.existsByEmail(request.email)) { "Email is already in use" }

        val user = UserEntity.create(
            name = request.name,
            password = passwordEncoder.encode(request.password),
            email = request.email,
        ).apply { userRepository.save(this) }

        return JoinUserResponse(
            id = user.id!!,
            email = user.email,
        )
    }

    private fun validateRequest(request: JoinUserRequest) {
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

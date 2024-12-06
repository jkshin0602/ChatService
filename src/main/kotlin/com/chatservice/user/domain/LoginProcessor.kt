package com.chatservice.user.domain

import com.chatservice.common.exception.NotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component

@Component
class LoginProcessor(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider
) {
    fun login(request: LoginRequest): Token {
        validateRequest(request)

        val user = userRepository.findByEmail(request.email) ?: throw NotFoundException("Not found user")

        require(passwordEncoder.matches(request.password, user.password)) {
            "Invalid password"
        }

        val token = jwtTokenProvider.createToken(
            userId = user.id!!,
            email = user.email,
            role = user.role.name,
        )
        return Token(token)
    }

    private fun validateRequest(request: LoginRequest) {
        require(request.email.isNotBlank()) { "Email cannot be blank" }
        require(request.password.isNotBlank()) { "Password cannot be blank" }
    }
}


data class LoginRequest(
    val email: String,
    val password: String,
)

@JvmInline
value class Token(
    val value: String,
)

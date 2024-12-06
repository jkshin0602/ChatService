package com.chatservice.user.domain

import org.springframework.stereotype.Component

@Component
class UserManager(
    private val userRepository: UserRepository,
) {
    fun existUser(userId: Long): Boolean {
        return userRepository.findByIdOrNull(userId) != null
    }
}

package com.chatservice.user.domain

import com.chatservice.user.entity.UserEntity
import org.springframework.stereotype.Component

@Component
class UserManager(
    private val userRepository: UserRepository,
) {
    fun existUser(userId: Long): Boolean {
        return userRepository.findByIdOrNull(userId) != null
    }

    fun getUser(userId: Long): UserEntity? = userRepository.findByIdOrNull(userId)
}

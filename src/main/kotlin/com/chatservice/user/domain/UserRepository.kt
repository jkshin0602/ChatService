package com.chatservice.user.domain

import com.chatservice.user.entity.UserEntity

interface UserRepository {
    fun save(user: UserEntity): UserEntity
    fun findByIdOrNull(id: Long): UserEntity?
    fun existsByEmail(email: String): Boolean
    fun findByEmail(email: String): UserEntity?
}

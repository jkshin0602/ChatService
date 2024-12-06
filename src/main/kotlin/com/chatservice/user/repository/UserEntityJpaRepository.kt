package com.chatservice.user.repository

import com.chatservice.user.domain.UserRepository
import com.chatservice.user.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface UserEntityJpaRepository : JpaRepository<UserEntity, Long>, UserRepository {
    @Query("select u from UserEntity u where u.id = :id")
    override fun findByIdOrNull(id: Long): UserEntity?
}

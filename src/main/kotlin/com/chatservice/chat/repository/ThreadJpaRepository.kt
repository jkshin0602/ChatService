package com.chatservice.chat.repository

import com.chatservice.chat.domain.ThreadRepository
import com.chatservice.chat.entity.ThreadEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query

interface ThreadJpaRepository : JpaRepository<ThreadEntity, Long>, ThreadRepository {
    @Query("select t from ThreadEntity t where t.userId = :userId order by t.lastModifiedDate desc limit 1")
    override fun findFirstByUserIdOrderByLastMessageAtDesc(userId: Long): ThreadEntity?
    @Query("select t from ThreadEntity t where t.id = :id")
    override fun findByIdOrNull(threadId: Long): ThreadEntity?
}

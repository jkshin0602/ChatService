package com.chatservice.chat.repository

import com.chatservice.chat.domain.PageThreadRepository
import com.chatservice.chat.entity.QThreadEntity.threadEntity
import com.chatservice.chat.entity.ThreadEntity
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Repository

@Repository
class DefaultPageThreadRepository(
    private val jpaQueryFactory: JPAQueryFactory,
): PageThreadRepository {
    override fun findAllByUserId(pageable: Pageable, userId: Long): Page<ThreadEntity> {
        val query = jpaQueryFactory.selectFrom(threadEntity)
            .where(threadEntity.userId.eq(userId))

        pageable.sort.forEach { order ->
            when(order.property) {
                "createdDate" -> {
                    if (order.isAscending) {
                        query.orderBy(threadEntity.createdDate.asc())
                    } else {
                        query.orderBy(threadEntity.createdDate.desc())
                    }
                }
            }
        }

        val content = query
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val total = jpaQueryFactory.select(threadEntity.count())
            .from(threadEntity)
            .where(threadEntity.userId.eq(userId))
            .fetchOne() ?: 0L

        return PageImpl(content, pageable, total)
    }

    override fun findAllById(pageable: Pageable): Page<ThreadEntity> {
        val query = jpaQueryFactory.selectFrom(threadEntity)
        pageable.sort.forEach { order ->
            when(order.property) {
                "createdDate" -> {
                    if (order.isAscending) {
                        query.orderBy(threadEntity.createdDate.asc())
                    } else {
                        query.orderBy(threadEntity.createdDate.desc())
                    }
                }
            }
        }
        val content = query
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())
            .fetch()

        val total = jpaQueryFactory.select(threadEntity.count())
            .from(threadEntity)
            .fetchOne() ?: 0L

        return PageImpl(content, pageable, total)
    }
}

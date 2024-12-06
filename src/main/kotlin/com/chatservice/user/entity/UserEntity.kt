package com.chatservice.user.entity

import com.chatservice.common.jpa_audit.BaseTimeEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.Table

@Table(name = "users")
@Entity
class UserEntity(
    @Column(name = "name", nullable = false)
    var name: String,
    @Column(name = "password", nullable = false)
    var password: String,
    @Column(name = "email", nullable = false)
    var email: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    var role: UserRole,
) : BaseTimeEntity() {
    companion object {
        fun create(
            name: String,
            password: String,
            email: String,
            role: UserRole = UserRole.MEMBER,
        ) = UserEntity(
            name = name,
            password = password,
            email = email,
            role = role,
        )
    }
}

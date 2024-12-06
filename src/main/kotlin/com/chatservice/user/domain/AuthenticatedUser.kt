package com.chatservice.user.domain

import com.chatservice.user.entity.UserEntity
import com.chatservice.user.entity.UserRole
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

@Target(AnnotationTarget.VALUE_PARAMETER)
@Retention(AnnotationRetention.RUNTIME)
@AuthenticationPrincipal(expression = "#this")
annotation class AuthUser

data class AuthenticatedUser(
    val id: Long,
    val email: String,
    val role: UserRole,
)

class CustomUserDetails(
    private val userEntity: UserEntity,
) : UserDetails {
    override fun getAuthorities(): List<SimpleGrantedAuthority> =
        listOf(SimpleGrantedAuthority("ROLE_${userEntity.role.name}"))

    override fun getPassword(): String {
        return userEntity.password
    }

    override fun getUsername(): String {
        return userEntity.email
    }
}

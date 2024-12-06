package com.chatservice.user.domain

import com.chatservice.user.entity.UserEntity
import com.chatservice.user.entity.UserRole
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jws
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import java.util.*

@Component
class JwtTokenProvider(
    @Value("\${jwt.secret}")
    private val secretKey: String,

    @Value("\${jwt.expiration-minutes}")
    private val expirationMinutes: Long,
) {
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    fun createToken(
        userId: Long,
        email: String,
        role: String,
    ): String {
        val now = Date()
        val validity = Date(now.time + expirationMinutes * 60 * 1000)

        return Jwts.builder()
            .subject(email)
            .claim("role", role)
            .claim("id", userId)
            .issuedAt(now)
            .expiration(validity)
            .signWith(key)
            .compact()
    }

    fun validateToken(token: String): Boolean {
        return try {
            Jwts.parser()
                .verifyWith(key)
                .build()
                .parseSignedClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    fun getAuthentication(token: String): Authentication {
        val claims = getClaims(token)
        val authorities = listOf(SimpleGrantedAuthority("ROLE_${claims.payload["role"]}"))

        val user = getUserInfoFromToken(token)

        val authDetail = CustomUserDetails(
            userEntity = UserEntity(
                name = user.email,
                password = "",
                email = user.email,
                role = user.role,
            )
        )

        return UsernamePasswordAuthenticationToken(
            authDetail,
            "",
            authorities,
        )
    }

    private fun getUserInfoFromToken(token: String): AuthenticatedUser {
        val claims = getClaims(token)
        return AuthenticatedUser(
            id = claims.payload["id"] as Long,
            email = claims.payload.subject,
            role = UserRole.valueOf(claims.payload["role"].toString()),
        )
    }

    private fun getClaims(token: String): Jws<Claims> {
        return Jwts.parser()
            .verifyWith(key)
            .build()
            .parseSignedClaims(token)
    }
}

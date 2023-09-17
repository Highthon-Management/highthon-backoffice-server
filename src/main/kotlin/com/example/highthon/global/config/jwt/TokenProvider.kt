package com.example.highthon.global.config.jwt

import com.example.highthon.domain.auth.entity.RefreshToken
import com.example.highthon.domain.auth.presentation.dto.response.TokenResponse
import com.example.highthon.domain.auth.repository.RefreshTokenRepository
import com.example.highthon.global.config.error.exception.ExpiredTokenException
import com.example.highthon.global.config.error.exception.InvalidTokenException
import com.example.highthon.global.config.jwt.env.TokenProperty
import com.example.highthon.global.config.security.principal.AuthDetails
import com.example.highthon.global.config.security.principal.AuthDetailsService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Component
class TokenProvider(
    private val refreshTokenRepository: RefreshTokenRepository,
    private val property: TokenProperty,
    private val authDetailsService: AuthDetailsService,
    private val tokenProvider: TokenProvider
) {

    private fun generateAccessToken(sub: String): String {
        return Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, property.secretKey)
            .setSubject(sub)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + property.accessExp))
            .compact()
    }

    private fun generateRefreshToken(sub: String): String {

        val oldRedis = refreshTokenRepository.findByPhoneNumber(sub)

        if (oldRedis != null) refreshTokenRepository.delete(oldRedis)

        val rfToken = Jwts.builder()
            .signWith(SignatureAlgorithm.HS256, property.secretKey)
            .setIssuedAt(Date())
            .setExpiration(Date(Date().time + property.refreshExp))
            .compact()

        refreshTokenRepository.save(RefreshToken(rfToken, sub))

        return rfToken
    }

    fun receiveToken(accountId: String) = TokenResponse (
        generateAccessToken(accountId),
        generateRefreshToken(accountId)
    )

    private fun getSubject(token: String): String {
        return try {
            Jwts.parser()
                .setSigningKey(property.secretKey)
                .parseClaimsJws(token).body.subject
        } catch (e: Exception) {
            when (e) {
                is ExpiredJwtException -> throw ExpiredTokenException
                else -> throw InvalidTokenException
            }
        }
    }

    fun getAuthentication(token: String): Authentication {

        val subject = getSubject(token)

        val authDetails = authDetailsService.loadUserByUsername(subject) as AuthDetails

        return UsernamePasswordAuthenticationToken(authDetails, "", authDetails.authorities)
    }

    fun reissue(token: String): TokenResponse{
        
        val refreshToken = refreshTokenRepository.findByIdOrNull(token)
            ?: throw InvalidTokenException

        refreshTokenRepository.delete(refreshToken)

        return tokenProvider.receiveToken(refreshToken.phoneNumber)
    }

}

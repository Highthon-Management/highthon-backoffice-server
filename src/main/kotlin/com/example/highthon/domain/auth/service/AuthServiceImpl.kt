package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.exception.PasswordNotMatchedException
import com.example.highthon.domain.auth.presentation.dto.LoginRequest
import com.example.highthon.domain.auth.presentation.dto.TokenResponse
import com.example.highthon.domain.user.exception.UserNotFoundException
import com.example.highthon.domain.user.repository.UserRepository
import com.example.highthon.global.config.jwt.TokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
    private val passwordEncoder: PasswordEncoder
): AuthService {

    override fun login(req: LoginRequest): TokenResponse {

        val user = userRepository.findByPhoneNumber(req.phoneNumber!!)
            ?: throw UserNotFoundException

        if(!passwordEncoder.matches(req.password, user.password)) throw PasswordNotMatchedException

        return tokenProvider.receiveToken(user.phoneNumber)
    }
}
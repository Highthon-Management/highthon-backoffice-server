package com.example.highthon.global.config.security.principal

import com.example.highthon.domain.user.repository.UserRepository
import com.example.highthon.domain.user.exception.UserNotFoundException
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component

@Component
class AuthDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(phoneNumber: String): UserDetails =
        AuthDetails(userRepository.findByPhoneNumber(phoneNumber) ?: throw UserNotFoundException)
}
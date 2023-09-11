package com.example.highthon.global.common.facade

import com.example.highthon.domain.user.entity.User
import com.example.highthon.domain.user.repository.UserRepository
import com.example.highthon.global.config.error.exception.InvalidTokenException
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component

@Component
class UserFacade(
    private val userRepository: UserRepository
) {

    fun getCurrentUser(): User = userRepository.findByPhoneNumber(SecurityContextHolder.getContext().authentication.name)
        ?: throw InvalidTokenException
}
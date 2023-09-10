package com.example.highthon.domain.user.service

import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse
import com.example.highthon.global.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userFacade: UserFacade
): UserService {

    override fun getProfile(): UserProfileResponse = userFacade.getCurrentUser().toResponse()
}
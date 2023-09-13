package com.example.highthon.domain.user.service

import com.example.highthon.domain.user.entity.User
import com.example.highthon.domain.user.presentation.dto.request.EditProfileRequest
import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse
import com.example.highthon.domain.user.repository.UserRepository
import com.example.highthon.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class UserServiceImpl(
    private val userFacade: UserFacade,
    private val userRepository: UserRepository
): UserService {

    override fun getProfile(): UserProfileResponse = userFacade.getCurrentUser().toResponse()

    override fun editProfile(req: EditProfileRequest): UserProfileResponse {

        val user = userFacade.getCurrentUser()

        val newUser = userRepository.save(User(
            user.id,
            req.name!!,
            user.phoneNumber,
            req.password!!,
            req.school!!,
            req.part,
            user.role
        ))

        return newUser.toResponse()
    }
}
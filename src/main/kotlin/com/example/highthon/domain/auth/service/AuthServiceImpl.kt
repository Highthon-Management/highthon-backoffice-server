package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.exception.NumberNotMatchedException
import com.example.highthon.domain.auth.exception.PasswordNotMatchedException
import com.example.highthon.domain.auth.presentation.dto.request.ChangePasswordRequest
import com.example.highthon.domain.auth.presentation.dto.request.ChangePhoneNumberRequest
import com.example.highthon.domain.auth.presentation.dto.request.LoginRequest
import com.example.highthon.domain.auth.presentation.dto.response.TokenResponse
import com.example.highthon.domain.auth.repository.QualificationRepository
import com.example.highthon.domain.user.entity.User
import com.example.highthon.domain.user.exception.UserNotFoundException
import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse
import com.example.highthon.domain.user.repository.UserRepository
import com.example.highthon.global.common.facade.UserFacade
import com.example.highthon.global.config.jwt.TokenProvider
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val qualificationRepository: QualificationRepository,
    private val userFacade: UserFacade,
    private val smsService: SmsService
): AuthService {

    override fun login(req: LoginRequest): TokenResponse {

        val user = userRepository.findByPhoneNumber(req.phoneNumber!!)
            ?: throw UserNotFoundException

        if(!passwordEncoder.matches(req.password, user.password)) throw PasswordNotMatchedException

        return tokenProvider.receiveToken(user.phoneNumber)
    }

    @Transactional
    override fun changePhoneNumber(req: ChangePhoneNumberRequest): UserProfileResponse {

        val user = userFacade.getCurrentUser()

        if (smsService.phoneNumberCheck(req.phoneNumber!!, req.number!!)) throw NumberNotMatchedException

        qualificationRepository.deleteById(req.phoneNumber)

        return userRepository.save(User(
            user.id,
            user.name,
            req.phoneNumber,
            user.password,
            user.school,
            user.part,
            user.role
        )).toResponse()
    }

    @Transactional
    override fun changePassword(req: ChangePasswordRequest): UserProfileResponse {

        val user = userFacade.getCurrentUser()

        if (smsService.passwordCheck(user, req.number!!)) throw NumberNotMatchedException

        qualificationRepository.deleteById(user.phoneNumber)

        return userRepository.save(User(
            user.id,
            user.name,
            user.phoneNumber,
            passwordEncoder.encode(req.password!!),
            user.school,
            user.part,
            user.role
        )).toResponse()
    }
}
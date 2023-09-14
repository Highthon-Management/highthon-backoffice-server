package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.entity.type.NumberType
import com.example.highthon.domain.auth.exception.*
import com.example.highthon.domain.auth.presentation.dto.request.EditPhoneNumberRequest
import com.example.highthon.domain.auth.presentation.dto.request.LoginRequest
import com.example.highthon.domain.auth.presentation.dto.response.TokenResponse
import com.example.highthon.domain.auth.repository.CertificationRepository
import com.example.highthon.domain.user.entity.User
import com.example.highthon.domain.user.exception.UserNotFoundException
import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse
import com.example.highthon.domain.user.repository.UserRepository
import com.example.highthon.global.common.facade.UserFacade
import com.example.highthon.global.config.jwt.TokenProvider
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class AuthServiceImpl(
    private val userRepository: UserRepository,
    private val tokenProvider: TokenProvider,
    private val passwordEncoder: PasswordEncoder,
    private val certificationRepository: CertificationRepository,
    private val userFacade: UserFacade
): AuthService {

    override fun login(req: LoginRequest): TokenResponse {

        val user = userRepository.findByPhoneNumber(req.phoneNumber!!)
            ?: throw UserNotFoundException

        if(!passwordEncoder.matches(req.password, user.password)) throw PasswordNotMatchedException

        return tokenProvider.receiveToken(user.phoneNumber)
    }

    @Transactional
    override fun editPhoneNumber(req: EditPhoneNumberRequest): UserProfileResponse {

        val user = userFacade.getCurrentUser()

        if (userFacade.getCurrentUser().phoneNumber == req.phoneNumber!!) throw PhoneNumberMatchedException

        val redisEntity = certificationRepository.findByIdOrNull(req.phoneNumber)
            ?: throw MessageNotSentYetException

        if (userRepository.existsByPhoneNumber(req.phoneNumber)) throw AlreadySignUpException

        if (redisEntity.type != NumberType.CHANGE) throw MessageTypeNotMatchedException

        if (redisEntity.number != req.number!!) throw NumberNotMatchedException

        certificationRepository.delete(redisEntity)

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
}
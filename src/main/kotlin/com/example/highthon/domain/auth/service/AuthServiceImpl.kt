package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.entity.type.NumberType
import com.example.highthon.domain.auth.exception.MessageTypeNotMatchedException
import com.example.highthon.domain.auth.exception.PasswordNotMatchedException
import com.example.highthon.domain.auth.presentation.dto.request.CheckNumberRequest
import com.example.highthon.domain.auth.presentation.dto.request.LoginRequest
import com.example.highthon.domain.auth.presentation.dto.request.SignUpRequest
import com.example.highthon.domain.auth.presentation.dto.response.TokenResponse
import com.example.highthon.domain.user.entity.User
import com.example.highthon.domain.user.entity.type.Part
import com.example.highthon.domain.user.entity.type.Role
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
    private val passwordEncoder: PasswordEncoder,
    private val smsService: SMSService
): AuthService {

    @Transactional
    override fun login(req: LoginRequest): TokenResponse {

        val user = userRepository.findByPhoneNumber(req.phoneNumber!!)
            ?: throw UserNotFoundException

        if(!passwordEncoder.matches(req.password, user.password)) throw PasswordNotMatchedException

        return tokenProvider.receiveToken(user.phoneNumber)
    }
     override fun signup(request: SignUpRequest,phoneNumber: String, number: Int) {
        val isVerificationCode = smsService.checkNumber(CheckNumberRequest(phoneNumber, number),NumberType.SIGN_UP)

        if (!isVerificationCode) {
            throw MessageTypeNotMatchedException
        }

        val user = User(
            name = request.name?: "",
            phoneNumber = request.phoneNumber?: "",
            school = request.school?: "",
            password = passwordEncoder.encode(request.password),
            role = Role.USER,
            part = Part.BACK_END
        )
        userRepository.save(user)
    }

}
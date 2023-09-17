package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.entity.RefreshToken
import com.example.highthon.domain.auth.presentation.dto.request.*
import com.example.highthon.domain.auth.presentation.dto.response.TokenResponse
import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse

interface AuthService {

    fun login(req: LoginRequest): TokenResponse

    fun changePhoneNumber(req: ChangePhoneNumberRequest): UserProfileResponse

    fun changePassword(req: ChangePasswordRequest): UserProfileResponse

    fun signup(request: SignUpRequest)

    fun reissue(req: ReissueRequest): TokenResponse
}

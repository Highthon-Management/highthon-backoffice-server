package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.presentation.dto.request.ChangePasswordRequest
import com.example.highthon.domain.auth.presentation.dto.request.ChangePhoneNumberRequest
import com.example.highthon.domain.auth.presentation.dto.request.LoginRequest
import com.example.highthon.domain.auth.presentation.dto.response.TokenResponse
import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse

interface AuthService {

    fun login(req: LoginRequest): TokenResponse

    fun changePhoneNumber(req: ChangePhoneNumberRequest): UserProfileResponse

    fun changePassword(req: ChangePasswordRequest): UserProfileResponse
}

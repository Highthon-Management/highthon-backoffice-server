package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.presentation.dto.request.EditPhoneNumberRequest
import com.example.highthon.domain.auth.presentation.dto.request.LoginRequest
import com.example.highthon.domain.auth.presentation.dto.response.TokenResponse
import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse

interface AuthService {

    fun login(req: LoginRequest): TokenResponse

    fun editPhoneNumber(req: EditPhoneNumberRequest): UserProfileResponse
}

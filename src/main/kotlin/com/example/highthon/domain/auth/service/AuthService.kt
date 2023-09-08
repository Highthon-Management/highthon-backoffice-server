package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.presentation.dto.request.LoginRequest
import com.example.highthon.domain.auth.presentation.dto.response.TokenResponse

interface AuthService {

    fun login(req: LoginRequest): TokenResponse
}

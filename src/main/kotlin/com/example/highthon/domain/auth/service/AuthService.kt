package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.presentation.dto.LoginRequest
import com.example.highthon.domain.auth.presentation.dto.TokenResponse

interface AuthService {

    fun login(req: LoginRequest): TokenResponse
}

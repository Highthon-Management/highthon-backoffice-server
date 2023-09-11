package com.example.highthon.domain.auth.presentation.dto.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)

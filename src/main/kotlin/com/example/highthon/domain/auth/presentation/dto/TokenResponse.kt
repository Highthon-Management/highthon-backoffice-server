package com.example.highthon.domain.auth.presentation.dto

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String
)

package com.example.highthon.global.config.jwt.env

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("jwt")
@ConstructorBinding
data class TokenProperty(
    val secretKey: String,
    val accessExp: Long,
    val refreshExp: Long
)
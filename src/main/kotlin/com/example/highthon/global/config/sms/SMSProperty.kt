package com.example.highthon.global.config.sms

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConfigurationProperties("sms")
@ConstructorBinding
data class SMSProperty(
    val apiKey: String,
    val apiSecret: String,
    val sender: String
)
package com.example.highthon.domain.auth.presentation

import com.example.highthon.domain.auth.presentation.dto.request.CertificateNumberRequest
import com.example.highthon.domain.auth.presentation.dto.request.LoginRequest
import com.example.highthon.domain.auth.presentation.dto.request.SMSRequest
import com.example.highthon.domain.auth.presentation.dto.response.TokenResponse
import com.example.highthon.domain.auth.service.AuthService
import com.example.highthon.domain.auth.service.SMSService
import net.nurigo.sdk.message.response.SingleMessageSentResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val smsService: SMSService,
    private val authService: AuthService
) {

    @PostMapping("/sms")
    @ResponseStatus(HttpStatus.CREATED)
    fun sendMessage(
        @RequestBody @Valid
        req: SMSRequest
    ): SingleMessageSentResponse? {
        return smsService.sendCheckNumber(req.phoneNumber!!)
    }

    @GetMapping("/sms/check")
    fun certificateNumber(
        @RequestBody @Valid
        req: CertificateNumberRequest
    ): Boolean {
        return smsService.certificateNumber(req)
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    fun login(
        @RequestBody @Valid
        req: LoginRequest
    ): TokenResponse = authService.login(req)
}
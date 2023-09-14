package com.example.highthon.domain.auth.presentation

import com.example.highthon.domain.auth.entity.type.NumberType
import com.example.highthon.domain.auth.presentation.dto.request.CheckNumberRequest
import com.example.highthon.domain.auth.presentation.dto.request.EditPhoneNumberRequest
import com.example.highthon.domain.auth.presentation.dto.request.LoginRequest
import com.example.highthon.domain.auth.presentation.dto.request.SMSRequest
import com.example.highthon.domain.auth.presentation.dto.response.TokenResponse
import com.example.highthon.domain.auth.service.AuthService
import com.example.highthon.domain.auth.service.SMSService
import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse
import net.nurigo.sdk.message.response.SingleMessageSentResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PatchMapping
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
    ): SingleMessageSentResponse? = smsService.sendSignUpMessage(req.phoneNumber!!)

    @GetMapping("/sms/check")
    fun checkNumber(
        @RequestBody @Valid
        req: CheckNumberRequest
    ): Boolean = smsService.checkNumber(req, NumberType.SIGN_UP)

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    fun login(
        @RequestBody @Valid
        req: LoginRequest
    ): TokenResponse = authService.login(req)

    @PostMapping("/sms/edit")
    @ResponseStatus(value = HttpStatus.CREATED)
    fun sendMessageForEdit(
        @RequestBody @Valid
        req: SMSRequest
    ): SingleMessageSentResponse? = smsService.sendEditMessage(req.phoneNumber!!)

    @GetMapping("/sms/edit/check")
    fun checkEditNumber(
        @RequestBody @Valid
        req: CheckNumberRequest
    ): Boolean = smsService.checkNumber(req, NumberType.CHANGE)

    @PatchMapping("/sms/edit")
    fun editPhoneNumber(
        @RequestBody @Valid
        req: EditPhoneNumberRequest
    ): UserProfileResponse = authService.editPhoneNumber(req)
}
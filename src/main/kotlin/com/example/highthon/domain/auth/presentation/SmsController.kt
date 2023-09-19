package com.example.highthon.domain.auth.presentation

import com.example.highthon.domain.auth.presentation.dto.request.PhoneNumberCheckRequest
import com.example.highthon.domain.auth.presentation.dto.request.PhoneNumberSmsRequest
import com.example.highthon.domain.auth.presentation.dto.request.SignUpCheckRequest
import com.example.highthon.domain.auth.presentation.dto.request.SignUpSmsRequest
import com.example.highthon.domain.auth.service.SmsService
import net.nurigo.sdk.message.response.SingleMessageSentResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/auth/sms")
class SmsController(
    private val smsService: SmsService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun sendSignUpMessage(
        @RequestBody @Valid
        req: SignUpSmsRequest
    ): SingleMessageSentResponse? = smsService.sendSingUpMessage(req)

    @PostMapping("/phone-number")
    @ResponseStatus(HttpStatus.CREATED)
    fun sendPhoneNumberMessage(
        @RequestBody @Valid
        req: PhoneNumberSmsRequest
    ): SingleMessageSentResponse? = smsService.sendPhoneNumberMessage(req)

    @GetMapping("/check")
    fun checkNumber(
        @RequestBody @Valid
        req: SignUpCheckRequest
    ): Boolean = smsService.singUpCheck(req.phoneNumber!!, req.number!!)

    @GetMapping("/check/phone-number")
    fun checkNumber(
        @RequestBody @Valid
        req: PhoneNumberCheckRequest
    ): Boolean = smsService.phoneNumberCheck(req.phoneNumber!!, req.number!!)
}
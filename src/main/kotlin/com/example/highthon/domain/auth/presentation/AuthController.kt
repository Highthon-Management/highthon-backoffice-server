package com.example.highthon.domain.auth.presentation

import com.example.highthon.domain.auth.presentation.dto.CertificateNumberRequest
import com.example.highthon.domain.auth.presentation.dto.SMSRequest
import com.example.highthon.domain.auth.service.SMSService
import net.nurigo.sdk.message.response.SingleMessageSentResponse
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val smsService: SMSService
) {

    @PostMapping("/sms")
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
}
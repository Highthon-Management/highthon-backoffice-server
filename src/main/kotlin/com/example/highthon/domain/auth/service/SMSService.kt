package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.presentation.dto.request.CertificateNumberRequest
import net.nurigo.sdk.message.response.SingleMessageSentResponse

interface SMSService {

    fun sendCheckNumber(phoneNumber: String): SingleMessageSentResponse?

    fun certificateNumber(req: CertificateNumberRequest): Boolean
}
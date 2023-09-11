package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.entity.type.NumberType
import com.example.highthon.domain.auth.presentation.dto.request.CheckNumberRequest
import net.nurigo.sdk.message.response.SingleMessageSentResponse

interface SMSService {

    fun checkNumber(req: CheckNumberRequest, type: NumberType): Boolean

    fun sendSignUpMessage(phoneNumber: String): SingleMessageSentResponse?

    fun sendEditMessage(phoneNumber: String): SingleMessageSentResponse?
}
package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.presentation.dto.request.*
import net.nurigo.sdk.message.response.SingleMessageSentResponse

interface SmsService {

    fun sendSingUpMessage(req: SignUpSmsRequest): SingleMessageSentResponse?

    fun sendPhoneNumberMessage(req: PhoneNumberSmsRequest): SingleMessageSentResponse?

    fun sendPasswordMessage(): SingleMessageSentResponse?

    fun singUpCheck(phoneNumber: String, number: Int): Boolean

    fun phoneNumberCheck(phoneNumber: String, number: Int): Boolean

    fun passwordCheck(number: Int): Boolean
}
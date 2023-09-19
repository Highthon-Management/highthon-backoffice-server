package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.presentation.dto.request.*
import com.example.highthon.domain.user.entity.User
import net.nurigo.sdk.message.response.SingleMessageSentResponse

interface SmsService {

    fun sendSingUpMessage(req: SignUpSmsRequest): SingleMessageSentResponse?

    fun sendPhoneNumberMessage(req: PhoneNumberSmsRequest): SingleMessageSentResponse?

    fun singUpCheck(phoneNumber: String, number: Int): Boolean

    fun phoneNumberCheck(phoneNumber: String, number: Int): Boolean
}
package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.entity.Certification
import com.example.highthon.domain.auth.exception.AlreadyPostedMessageException
import com.example.highthon.domain.auth.exception.MessageNotSentYetException
import com.example.highthon.domain.auth.presentation.dto.request.CertificateNumberRequest
import com.example.highthon.domain.auth.repository.CertificationRepository
import com.example.highthon.global.config.sms.SMSProperty
import net.nurigo.sdk.NurigoApp.initialize
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.model.MessageType
import net.nurigo.sdk.message.request.SingleMessageSendingRequest
import net.nurigo.sdk.message.response.SingleMessageSentResponse
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class SMSServiceImpl(
    private val property: SMSProperty,
    private val certificationRepository: CertificationRepository
): SMSService {

    val messageService: DefaultMessageService = initialize(property.apiKey, property.apiSecret, "https://api.coolsms.co.kr")
    override fun sendCheckNumber(phoneNumber: String): SingleMessageSentResponse? {

        if (certificationRepository.existsById(phoneNumber)) throw AlreadyPostedMessageException

        val ran = Random().nextInt(1000000)

//        val variables = mutableMapOf<String, String>()
//        variables[phoneNumber] = ran.toString()
//
//        val kakaoOption = KakaoOption(
//            pfId = "pfId 입력",
//            templateId = "templateId 입력",
//            disableSms = false,
//            variables = variables
//        )

        val message = Message(
            from = property.sender,
            to = phoneNumber,
            text = "\n[Highthon 휴대폰 인증]\n하이톤 회원가입 인증번호 $ran 입니다.",
            type = MessageType.SMS,
            country = "+82"
        )

        certificationRepository.save(Certification(phoneNumber, ran))

        return messageService.sendOne(SingleMessageSendingRequest(message))
    }

    override fun certificateNumber(req: CertificateNumberRequest): Boolean {
        val redisEntity = certificationRepository.findByIdOrNull(req.phoneNumber!!)
            ?: throw MessageNotSentYetException

        return redisEntity.number == req.number!!
    }
}
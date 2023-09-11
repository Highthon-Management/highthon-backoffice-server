package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.entity.Certification
import com.example.highthon.domain.auth.entity.type.NumberType
import com.example.highthon.domain.auth.exception.*
import com.example.highthon.domain.auth.presentation.dto.request.CheckNumberRequest
import com.example.highthon.domain.auth.repository.CertificationRepository
import com.example.highthon.domain.user.repository.UserRepository
import com.example.highthon.global.common.facade.UserFacade
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
    private val certificationRepository: CertificationRepository,
    private val userFacade: UserFacade,
    private val userRepository: UserRepository
): SMSService {

    val messageService: DefaultMessageService = initialize(property.apiKey, property.apiSecret, "https://api.coolsms.co.kr")

    override fun checkNumber(req: CheckNumberRequest,  type: NumberType): Boolean {

        val redisEntity = certificationRepository.findByIdOrNull(req.phoneNumber!!)
            ?: throw MessageNotSentYetException

        if (redisEntity.type != type) throw MessageTypeNotMatchedException

        return redisEntity.number == req.number!!
    }

    @Transactional
    override fun sendSignUpMessage(phoneNumber: String): SingleMessageSentResponse? = sendMessage(phoneNumber, NumberType.SIGN_UP)

    private fun sendMessage(phoneNumber: String, type: NumberType): SingleMessageSentResponse? {

        if (userRepository.existsByPhoneNumber(phoneNumber)) throw AlreadySignUpException

        if (certificationRepository.existsById(phoneNumber)) throw AlreadyPostedMessageException

        val ran = Random().nextInt(1000000)

        /*
        val variables = mutableMapOf<String, String>()
        variables[phoneNumber] = ran.toString()

        val kakaoOption = KakaoOption(
        pfId = "pfId 입력",
        templateId = "templateId 입력",
        disableSms = false,
        variables = variables
        )
        */

        val message = Message(
            from = property.sender,
            to = phoneNumber,
            text = "\n[Highthon 휴대폰 인증]\n하이톤 ${if (type == NumberType.SIGN_UP) "회원가입" else "전화번호 변경"} 인증번호 $ran 입니다.",
            type = MessageType.SMS,
            country = "+82"
        )

        certificationRepository.save(Certification(phoneNumber, ran, type))

        return messageService.sendOne(SingleMessageSendingRequest(message))
    }

    @Transactional
    override fun sendEditMessage(phoneNumber: String): SingleMessageSentResponse? {

        if (userFacade.getCurrentUser().phoneNumber != phoneNumber) throw PhoneNumberMatchedException

        return sendMessage(phoneNumber, NumberType.CHANGE)
    }

}
package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.entity.Qualification
import com.example.highthon.domain.auth.entity.type.NumberType
import com.example.highthon.domain.auth.exception.*
import com.example.highthon.domain.auth.presentation.dto.request.*
import com.example.highthon.domain.auth.repository.QualificationRepository
import com.example.highthon.domain.user.entity.User
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
class SmsServiceImpl(
    private val property: SMSProperty,
    private val qualificationRepository: QualificationRepository,
    private val userFacade: UserFacade,
    private val userRepository: UserRepository
): SmsService {

    val messageService: DefaultMessageService = initialize(property.apiKey, property.apiSecret, "https://api.coolsms.co.kr")


    private fun sendMessage(phoneNumber: String, text: String, messageType: NumberType, ran: Int): SingleMessageSentResponse? {

        val message = Message(
            from = property.sender,
            to = phoneNumber,
            text = text,
            type = MessageType.SMS,
            country = "+82"
        )

        qualificationRepository.save(Qualification(phoneNumber, ran, messageType))

        return messageService.sendOne(SingleMessageSendingRequest(message))
    }

    @Transactional
    override fun sendSingUpMessage(req: SignUpSmsRequest): SingleMessageSentResponse? {

        if (userRepository.existsByPhoneNumber(req.phoneNumber!!)) throw AlreadySignUpException

        if (qualificationRepository.existsById(req.phoneNumber)) throw AlreadyPostedMessageException

        val ran = Random().nextInt(999999)

        return sendMessage(
            req.phoneNumber,
            "\n[Highthon 휴대폰 인증]\n하이톤 회원가입 인증번호 $ran 입니다.",
            NumberType.SIGN_UP,
            ran
        )
    }

    @Transactional
    override fun sendPhoneNumberMessage(req: PhoneNumberSmsRequest): SingleMessageSentResponse? {

        if (userRepository.existsByPhoneNumber(req.phoneNumber!!)) throw AlreadySignUpException

        if (qualificationRepository.existsById(req.phoneNumber)) throw AlreadyPostedMessageException

        val ran = Random().nextInt(999999)

        return sendMessage(
            req.phoneNumber,
            "\n[Highthon 휴대폰 인증]\n하이톤 전화번호 변경 인증번호 $ran 입니다.",
            NumberType.CHANGE_PHONE_NUMBER,
            ran
        )
    }

    @Transactional
    override fun sendPasswordMessage(): SingleMessageSentResponse? {

        val user = userFacade.getCurrentUser()

        if (qualificationRepository.existsById(user.phoneNumber)) throw AlreadyPostedMessageException

        val ran = Random().nextInt(999999)

        return sendMessage(
            user.phoneNumber,
            "\n[Highthon 휴대폰 인증]\n하이톤 비밀번호 변경 인증번호 $ran 입니다.",
            NumberType.CHANGE_PASSWORD,
            ran
        )
    }

    override fun singUpCheck(phoneNumber: String, number: Int): Boolean {

        val qualification = qualificationRepository.findByIdOrNull(phoneNumber)
            ?: throw MessageNotSentYetException

        if (qualification.type != NumberType.SIGN_UP) throw MessageTypeNotMatchedException

        return qualification.number == number
    }

    override fun phoneNumberCheck(phoneNumber: String, number: Int): Boolean {

        val qualification = qualificationRepository.findByIdOrNull(phoneNumber)
            ?: throw MessageNotSentYetException

        if (qualification.type != NumberType.CHANGE_PHONE_NUMBER) throw MessageTypeNotMatchedException

        return qualification.number == number
    }

    override fun passwordCheck(user: User, number: Int): Boolean {

        val qualification = qualificationRepository.findByIdOrNull(user.phoneNumber)
            ?: throw MessageNotSentYetException

        if (qualification.type != NumberType.CHANGE_PASSWORD) throw MessageTypeNotMatchedException

        return qualification.number == number
    }
}
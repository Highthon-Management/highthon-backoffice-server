package com.example.highthon.domain.auth.service

import com.example.highthon.domain.auth.entity.Qualification
import com.example.highthon.domain.auth.entity.type.NumberType
import com.example.highthon.domain.auth.exception.*
import com.example.highthon.domain.auth.presentation.dto.request.PhoneNumberSmsRequest
import com.example.highthon.domain.auth.presentation.dto.request.SignUpSmsRequest
import com.example.highthon.domain.auth.repository.QualificationRepository
import com.example.highthon.domain.user.repository.UserRepository
import com.example.highthon.global.env.sms.SmsProperty
import mu.KLogger
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
    private val property: SmsProperty,
    private val qualificationRepository: QualificationRepository,
    private val userRepository: UserRepository,
    private val messageService: DefaultMessageService,
    private val logger: KLogger
): SmsService {

    private fun sendMessage(phoneNumber: String, text: String, messageType: NumberType, ran: Int): SingleMessageSentResponse {

        val message = Message(
            from = property.sender,
            to = phoneNumber,
            text = text,
            type = MessageType.SMS,
            country = "+82"
        )

        var res: SingleMessageSentResponse? = null
        try {
            res = messageService.sendOne(SingleMessageSendingRequest(message))

        } catch (e: Exception){
            logger.error { e.localizedMessage }
            logger.error { e.stackTrace }
        }
        qualificationRepository.save(Qualification(phoneNumber, ran, messageType))

        return res ?: throw PhoneNumberNotExistException
    }

    @Transactional
    override fun sendSingUpMessage(req: SignUpSmsRequest): SingleMessageSentResponse? {

        if (userRepository.existsByPhoneNumber(req.phoneNumber!!)) throw AlreadySignUpException

        if (qualificationRepository.existsById(req.phoneNumber)) throw AlreadyPostedMessageException

        val ran = Random().nextInt(1000000)

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

        val ran = Random().nextInt(1000000)

        return sendMessage(
            req.phoneNumber,
            "\n[Highthon 휴대폰 인증]\n하이톤 전화번호 변경 인증번호 $ran 입니다.",
            NumberType.CHANGE_PHONE_NUMBER,
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
}
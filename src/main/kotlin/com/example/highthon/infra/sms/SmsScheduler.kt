package com.example.highthon.infra.sms

import com.example.highthon.domain.user.entity.type.Role
import com.example.highthon.domain.user.repository.UserRepository
import com.example.highthon.global.env.sms.SmsProperty
import mu.KLogger
import net.nurigo.sdk.message.model.Message
import net.nurigo.sdk.message.model.MessageType
import net.nurigo.sdk.message.service.DefaultMessageService
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class SmsScheduler(
    private val userRepository: UserRepository,
    private val property: SmsProperty,
    private val messageService: DefaultMessageService,
    private val logger: KLogger
) {

    @Transactional
    @Scheduled(cron = "0 0 8 1 1 ?", zone = "Asia/Seoul") // 추후에 날짜 변경할 것
    fun sendMessageToParticipant() {

        val messageList = userRepository.findAllByRole(Role.PARTICIPANT)?.map {
            Message(
                from = property.sender,
                to = it.phoneNumber,
                text = "안녕하세요 하이톤 운영진입니다. \n우선 9회 하이톤에 지원해주셔서 감사합니다. \n\n" +
                        "귀하의 뛰어난 역량과 잠재력에도 불구하고 예상보다 많은 지원자들로 인해 하이톤에 참여하실 수 없음을 통보 드립니다. " +
                        "\n\n지원자님께서 부족하고 모자라서가 아닙니다. 더 많은 분을 모시지 못하는 저희의 잘못입니다. " +
                        "더욱 노력하여 많은 분들을 모실 수 있도록 하겠습니다.",
                type = MessageType.LMS,
                country = "+82"
            )
        }?.toMutableList()

        sendMessages(messageList)
    }

    @Transactional
    @Scheduled(cron = "0 0 8 1 1 ?", zone = "Asia/Seoul") // 추후에 날짜 변경할 것
    fun sendMessageToWaiting() {

        val messageList = userRepository.findAllByRole(Role.WAITING)?.map {
            Message(
                from = property.sender,
                to = it.phoneNumber,
                text = "안녕하세요 하이톤 운영진입니다. \n우선 9회 하이톤에 지원해주셔서 감사합니다. \n\n" +
                        "9회 하이톤 지원 결과 예비 합격자로 선정되셨습니다.\n" +
                        "예비 합격자의 경우 기존 합격자가 포기할 경우 하이톤에 참여자격이 부여됩니다.\n\n" +
                        "2024년 1월 11일 이내에 포기자가 발생하는 경우, 예비 합격자분들에게 메세지로 다시 연락 드리겠습니다.\n" +
                        "별도 안내가 없을 경우 포기자가 없는 경우로 안타깝게 최종 불합격입니다.",
                type = MessageType.LMS,
                country = "+82"
            )
        }?.toMutableList()
        sendMessages(messageList)
    }

    @Transactional
    @Scheduled(cron = "0 0 8 1 1 ?", zone = "Asia/Seoul") // 추후에 날짜 변경할 것
    fun sendMessageToConfirmed() {

        val messageList = userRepository.findAllByRole(Role.ADMIN)?.map {
            Message(
                from = property.sender,
                to = it.phoneNumber,
                text = "안녕하세요 하이톤 운영진입니다. \n\n" +
                        "9회 하이톤 지원 결과 합격자로 선정 되셨습니다.\n\n" +
                        "2024년 1월 10일까지 취소 하실 수 있으며, 그 이후의 취소를 요청하실 경우 참가비 환불 및 앞으로 하이톤에 지원은 불가능합니다. ",
                type = MessageType.LMS,
                country = "+82"
            )
        }?.toMutableList()
        sendMessages(messageList)
    }

    private fun sendMessages(messageList: MutableList<Message>?) {

        if (messageList.isNullOrEmpty()) return

        try {
            messageService.send(messageList)
            logger.info { "send messages complete" }
        } catch (e: Exception) {
            logger.error { e.message }
            logger.error { e.stackTrace }
        }
    }
}
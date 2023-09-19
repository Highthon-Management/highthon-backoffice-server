package com.example.highthon.domain.apply.service

import com.example.highthon.domain.apply.entity.Applicant
import com.example.highthon.domain.user.entity.type.Part
import com.example.highthon.domain.apply.exception.AlreadyAppliedException
import com.example.highthon.domain.apply.exception.AlreadyCanceledApplyException
import com.example.highthon.domain.apply.exception.ApplicantNotFoundException
import com.example.highthon.domain.apply.exception.PermissionDeniedException
import com.example.highthon.domain.apply.presentaion.dto.request.ApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.request.EditApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.response.ApplicantDetailResponse
import com.example.highthon.domain.apply.presentaion.dto.response.ApplicantListResponse
import com.example.highthon.domain.apply.repository.ApplicantRepository
import com.example.highthon.domain.user.entity.User
import com.example.highthon.domain.user.entity.type.Role
import com.example.highthon.domain.user.repository.UserRepository
import com.example.highthon.global.common.facade.UserFacade
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import java.util.Base64.Encoder

@Service
@Transactional(readOnly = true)
class ApplicantServiceImpl(
    private val applicantRepository: ApplicantRepository,
    private val userFacade: UserFacade,
    private val bankEncoder: Encoder,
    private val userRepository: UserRepository
): ApplicantService {

    @Transactional
    override fun apply(req: ApplyRequest): ApplicantDetailResponse {

        val user = userFacade.getCurrentUser()

        if (applicantRepository.existsById(user.id!!)) throw AlreadyAppliedException

        val applicant = applicantRepository.save(Applicant(
            null,
            user,
            req.motivation!!,
            req.githubLink,
            bankAccount = bankEncoder.encodeToString(req.bankAccount!!.toByteArray()),
            bankType = req.bankType!!
        ))

        return applicant.toResponse()
    }

    @Transactional
    override fun edit(req: EditApplyRequest): ApplicantDetailResponse {

        val user = userFacade.getCurrentUser()

        val applicant = applicantRepository.findByIdOrNull(user.id!!)
            ?: throw ApplicantNotFoundException

        return applicantRepository.save(Applicant(
            user.id!!,
            user,
            req.motivation!!,
            req.githubLink,
            applicant.isCanceled,
            applicant.reason,
            applicant.bankAccount,
            applicant.bankType
        )).toResponse()
    }

    @Transactional
    override fun cancel(reason: String) {

        val user = userFacade.getCurrentUser()

        val applicant = applicantRepository.findByIdOrNull(user.id!!)
            ?: throw ApplicantNotFoundException

        if (applicant.isCanceled) throw AlreadyCanceledApplyException

        applicantRepository.save(Applicant(
            user.id!!,
            user,
            applicant.motivation,
            applicant.github,
            true,
            reason,
            applicant.bankAccount,
            applicant.bankType
        ))
    }

    override fun getDetail(id: UUID): ApplicantDetailResponse {

        val user = userFacade.getCurrentUser()

        val apply = applicantRepository.findByIdOrNull(id)
            ?: throw ApplicantNotFoundException

        if (user.role != Role.ADMIN && user.id!! != apply.id) throw PermissionDeniedException

        return apply.toResponse()
    }

    override fun getListByPart(idx: Int, size: Int, part: Part?): Page<ApplicantListResponse> {

        if (userFacade.getCurrentUser().role != Role.ADMIN) throw PermissionDeniedException

        return if (part == null) {
            applicantRepository.findAllByIsCanceledAndUserRole(
                false,
                Role.PARTICIPANT,
                PageRequest.of(
                    idx,
                    size,
                    Sort.by(Sort.Direction.DESC, "createdAt")
                )
            ).map {
                it.toMinimumResponse()
            }
        } else {
            applicantRepository.findAllByAndUserPartAndIsCanceledAndUserRole(
                part,
                false,
                Role.PARTICIPANT,
                PageRequest.of(
                    idx,
                    size,
                    Sort.by(Sort.Direction.DESC, "createdAt")
                )
            ).map {
                it.toMinimumResponse()
            }
        }
    }

    override fun getCanceledList(idx: Int, size: Int): Page<ApplicantListResponse> {

        if (userFacade.getCurrentUser().role != Role.ADMIN) throw PermissionDeniedException

        return applicantRepository.findAllByIsCanceledAndUserRole(
            true,
            Role.PARTICIPANT,
            PageRequest.of(
                idx,
                size,
                Sort.by(Sort.Direction.DESC, "createdAt")
            )
        ).map {
            it.toMinimumResponse()
        }
    }

    @Transactional
    override fun empowerment(id: UUID, role: Role): ApplicantDetailResponse {

        val applicant = applicantRepository.findByIdOrNull(id)
            ?: throw ApplicantNotFoundException

        if ((userFacade.getCurrentUser().role != Role.ADMIN || applicant.user.role == Role.ADMIN) || //토큰이 어드민이 아니거나, 변경하려는 신청의 권한이 ADMIN인 경우
            (role != Role.WAITING && role != Role.CONFIRMED)) throw PermissionDeniedException //올바른 권한으로 변경하지 않는 경우

        userRepository.save(User(
            applicant.user.id,
            applicant.user.name,
            applicant.user.phoneNumber,
            applicant.user.password,
            applicant.user.school,
            applicant.user.grade,
            applicant.user.part,
            role
        ))

        return applicant.toResponse()
    }
}
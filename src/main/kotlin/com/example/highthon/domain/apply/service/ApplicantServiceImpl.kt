package com.example.highthon.domain.apply.service

import com.example.highthon.domain.apply.entity.Applicant
import com.example.highthon.domain.user.entity.type.Part
import com.example.highthon.domain.apply.exception.AlreadyAppliedException
import com.example.highthon.domain.apply.exception.AlreadyCanceledApplyException
import com.example.highthon.domain.apply.exception.ApplyNotFoundException
import com.example.highthon.domain.apply.exception.PermissionDeniedException
import com.example.highthon.domain.apply.presentaion.dto.request.ApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.request.EditApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyDetailResponse
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyListResponse
import com.example.highthon.domain.apply.repository.ApplicantRepository
import com.example.highthon.domain.user.entity.type.Role
import com.example.highthon.global.common.facade.UserFacade
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Sort
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class ApplicantServiceImpl(
    private val applicantRepository: ApplicantRepository,
    private val userFacade: UserFacade
): ApplicantService {

    @Transactional
    override fun apply(req: ApplyRequest): ApplyDetailResponse {

        val user = userFacade.getCurrentUser()

        if (applicantRepository.existsById(user.id!!)) throw AlreadyAppliedException

        val applicant = applicantRepository.save(Applicant(
            null,
            user,
            req.motivation!!,
            req.githubLink,
            bankAccount = req.bankAccount!!
        ))

        return applicant.toResponse()
    }

    @Transactional
    override fun edit(req: EditApplyRequest): ApplyDetailResponse {

        val user = userFacade.getCurrentUser()

        val applicant = applicantRepository.findByIdOrNull(user.id!!)
            ?: throw ApplyNotFoundException

        return applicantRepository.save(Applicant(
            user.id!!,
            user,
            req.motivation!!,
            req.githubLink,
            applicant.isCanceled,
            applicant.reason,
            applicant.bankAccount
        )).toResponse()
    }

    @Transactional
    override fun cancel(reason: String) {

        val user = userFacade.getCurrentUser()

        val applicant = applicantRepository.findByIdOrNull(user.id!!)
            ?: throw ApplyNotFoundException

        if (applicant.isCanceled) throw AlreadyCanceledApplyException

        applicantRepository.save(Applicant(
            user.id!!,
            user,
            applicant.motivation,
            applicant.github,
            true,
            reason,
            applicant.bankAccount
        ))
    }

    override fun getDetail(id: UUID): ApplyDetailResponse {

        val user = userFacade.getCurrentUser()

        val apply = applicantRepository.findByIdOrNull(id)
            ?: throw ApplyNotFoundException

        if (user.role != Role.ADMIN && user.id!! != apply.id) throw PermissionDeniedException

        return apply.toResponse()
    }

    override fun getListByPart(idx: Int, size: Int, part: Part?): Page<ApplyListResponse> {

        if (userFacade.getCurrentUser().role != Role.ADMIN) throw PermissionDeniedException

        return if (part == null) {
            applicantRepository.findAllByIsCanceled(
                false,
                PageRequest.of(
                    idx,
                    size,
                    Sort.by(Sort.Direction.DESC, "createdAt")
                )
            ).map {
                it.toMinimumResponse()
            }
        } else {
            applicantRepository.findAllByAndUserPartAndIsCanceled(
                part,
                false,
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

    override fun getCanceledList(idx: Int, size: Int): Page<ApplyListResponse> {

        if (userFacade.getCurrentUser().role != Role.ADMIN) throw PermissionDeniedException

        return applicantRepository.findAllByIsCanceled(
            true,
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
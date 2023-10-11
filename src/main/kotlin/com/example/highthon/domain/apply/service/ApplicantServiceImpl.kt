package com.example.highthon.domain.apply.service

import com.example.highthon.domain.apply.entity.Applicant
import com.example.highthon.domain.apply.exception.AlreadyAppliedException
import com.example.highthon.domain.apply.exception.AlreadyCanceledApplyException
import com.example.highthon.domain.apply.exception.ApplicantNotFoundException
import com.example.highthon.domain.apply.exception.PermissionDeniedException
import com.example.highthon.domain.apply.presentaion.dto.request.ApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.request.EditApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.response.ApplicantDetailResponse
import com.example.highthon.domain.apply.presentaion.dto.response.ApplicantListResponse
import com.example.highthon.domain.apply.repository.ApplicantRepository
import com.example.highthon.domain.apply.repository.ApplicantRepositoryCustom
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
    private val userRepository: UserRepository,
    private val customApplicantRepository: ApplicantRepositoryCustom
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

        adminChecker(user)

        val apply = applicantRepository.findByIdOrNull(id)
            ?: throw ApplicantNotFoundException

        if (user.id!! != apply.id) throw PermissionDeniedException

        return apply.toResponse()
    }

    override fun getCanceledList(idx: Int, size: Int): Page<ApplicantListResponse> {

        adminChecker(userFacade.getCurrentUser())
        
        return customApplicantRepository
            .findCanceledAllByUserRole(Role.PARTICIPANT, PageRequest.of(idx, size, Sort.by("createdAt").descending()))
    }

    @Transactional
    override fun empowerment(id: UUID, role: Role): ApplicantDetailResponse {

        val applicant = applicantRepository.findByIdOrNull(id)
            ?: throw ApplicantNotFoundException

        adminChecker(userFacade.getCurrentUser())

        if (applicant.user.role == Role.ADMIN || role != Role.WAITING && role != Role.CONFIRMED) throw PermissionDeniedException

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

    private fun adminChecker(user: User) {
        if (user.role != Role.ADMIN) throw PermissionDeniedException
    }
    override fun getListSortedByPartDESC(idx: Int, size: Int): Page<ApplicantListResponse> {
        adminChecker(userFacade.getCurrentUser())
        return customApplicantRepository.findApplicantSortedByPartDESC(PageRequest.of(idx, size, Sort.by("createdAt").descending()))
    }
    override fun getListSortedByPartASC(idx: Int, size: Int): Page<ApplicantListResponse> {
        adminChecker(userFacade.getCurrentUser())
        return customApplicantRepository.findApplicantSortedByPartASC(PageRequest.of(idx, size, Sort.by("createdAt").descending()))
    }

    override fun getListSortedByGradeDESC(idx: Int, size: Int): Page<ApplicantListResponse> {
        adminChecker(userFacade.getCurrentUser())
        return customApplicantRepository.findApplicantSortedByGradeDESC(PageRequest.of(idx, size, Sort.by("createdAt").descending()))
    }

    override fun getListSortedByGradeASC(idx: Int, size: Int): Page<ApplicantListResponse> {
        adminChecker(userFacade.getCurrentUser())
        return customApplicantRepository.findApplicantSortedByGradeASC(PageRequest.of(idx, size, Sort.by("createdAt").descending()))
    }

    override fun getListSortedBySchoolDESC(idx: Int, size: Int): Page<ApplicantListResponse> {
        adminChecker(userFacade.getCurrentUser())
        return customApplicantRepository.findApplicantSortedBySchoolDESC(PageRequest.of(idx, size, Sort.by("createdAt").descending()))
    }

    override fun getListSortedBySchoolASC(idx: Int, size: Int): Page<ApplicantListResponse> {
        adminChecker(userFacade.getCurrentUser())
        return customApplicantRepository.findApplicantSortedBySchoolASC(PageRequest.of(idx, size, Sort.by("createdAt").descending()))
    }
}
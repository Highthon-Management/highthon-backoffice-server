package com.example.highthon.domain.apply.service

import com.example.highthon.domain.apply.entity.Apply
import com.example.highthon.domain.apply.entity.Part
import com.example.highthon.domain.apply.exception.AlreadyAppliedException
import com.example.highthon.domain.apply.exception.AlreadyCanceledApplyException
import com.example.highthon.domain.apply.exception.ApplyNotFoundException
import com.example.highthon.domain.apply.exception.PermissionDeniedException
import com.example.highthon.domain.apply.presentaion.dto.request.ApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyDetailResponse
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyListResponse
import com.example.highthon.domain.apply.repository.ApplyRepository
import com.example.highthon.domain.user.entity.type.Role
import com.example.highthon.domain.user.repository.UserRepository
import com.example.highthon.global.common.facade.UserFacade
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional(readOnly = true)
class ApplyServiceImpl(
    private val applyRepository: ApplyRepository,
    private val userFacade: UserFacade,
    private val userRepository: UserRepository
): ApplyService {

    @Transactional
    override fun apply(req: ApplyRequest): ApplyDetailResponse {

        val user = userFacade.getCurrentUser()

        if (applyRepository.existsById(user.id!!)) throw AlreadyAppliedException

        val apply = applyRepository.save(Apply(
            user,
            req.motivation!!,
            req.githubLink
        ))

        return apply.toResponse()
    }

    @Transactional
    override fun edit(req: ApplyRequest): ApplyDetailResponse {

        val user = userFacade.getCurrentUser()

        if (!applyRepository.existsById(user.id!!)) throw ApplyNotFoundException

        val apply = applyRepository.save(Apply(
            user,
            req.motivation!!,
            req.githubLink
        ))

        return apply.toResponse()
    }

    @Transactional
    override fun cancel(reason: String) {

        val user = userFacade.getCurrentUser()

        val apply = applyRepository.findByIdOrNull(user.id!!)
            ?: throw ApplyNotFoundException

        if (apply.isCanceled) throw AlreadyCanceledApplyException

        applyRepository.save(Apply(
            user,
            apply.motivation,
            apply.github,
            true,
            reason
        ))
    }

    override fun getDetail(id: UUID): ApplyDetailResponse {

        val user = userFacade.getCurrentUser()

        val apply = applyRepository.findByIdOrNull(id)
            ?: throw ApplyNotFoundException

        if (user.role != Role.ADMIN && user.id!! != apply.id) throw PermissionDeniedException

        return apply.toResponse()
    }

    override fun getList(idx: Int, size: Int, part: Part): Page<ApplyListResponse> {

        val user = userFacade.getCurrentUser()

        if (user.role != Role.ADMIN) throw PermissionDeniedException

        return applyRepository.findAllByAndUserPart(
            part,
            PageRequest.of(
                idx,
                size
            )
        ).map {
            it.toMinimumResponse()
        }
    }
}
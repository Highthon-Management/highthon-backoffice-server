package com.example.highthon.domain.apply.service

import com.example.highthon.domain.apply.entity.Apply
import com.example.highthon.domain.apply.exception.AlreadyAppliedException
import com.example.highthon.domain.apply.exception.AlreadyCanceledApplyException
import com.example.highthon.domain.apply.exception.ApplyNotFoundException
import com.example.highthon.domain.apply.exception.PermissionDeniedException
import com.example.highthon.domain.apply.presentaion.dto.request.ApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyResponse
import com.example.highthon.domain.apply.repository.ApplyRepository
import com.example.highthon.domain.user.entity.type.Role
import com.example.highthon.domain.user.exception.UserNotFoundException
import com.example.highthon.domain.user.repository.UserRepository
import com.example.highthon.global.common.facade.UserFacade
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
    override fun apply(req: ApplyRequest): ApplyResponse {

        val user = userFacade.getCurrentUser()

        if (applyRepository.existsById(user.pk!!)) throw AlreadyAppliedException

        val apply = applyRepository.save(Apply(
            user.pk!!,
            req.motivation!!,
            req.part!!,
            req.githubLink
        ))

        return apply.toResponse(user)
    }

    @Transactional
    override fun edit(req: ApplyRequest): ApplyResponse {

        val user = userFacade.getCurrentUser()

        if (!applyRepository.existsById(user.pk!!)) throw ApplyNotFoundException

        val apply = applyRepository.save(Apply(
            user.pk!!,
            req.motivation!!,
            req.part!!,
            req.githubLink
        ))

        return apply.toResponse(user)
    }

    @Transactional
    override fun cancel(reason: String) {

        val user = userFacade.getCurrentUser()

        val apply = applyRepository.findByIdOrNull(user.pk!!)
            ?: throw ApplyNotFoundException

        if (apply.isCanceled) throw AlreadyCanceledApplyException

        applyRepository.save(Apply(
            user.pk!!,
            apply.motivation,
            apply.part,
            apply.github,
            true,
            reason
        ))
    }

    override fun getDetail(id: UUID): ApplyResponse {

        val user = userFacade.getCurrentUser()

        val apply = applyRepository.findByIdOrNull(id)
            ?: throw ApplyNotFoundException

        if (user.role != Role.ADMIN && user.pk!! != apply.id) throw PermissionDeniedException

        return apply.toResponse(userRepository.findByPk(apply.id)!!)
    }
}
package com.example.highthon.domain.apply.service

import com.example.highthon.domain.apply.entity.Apply
import com.example.highthon.domain.apply.exception.AlreadyAppliedException
import com.example.highthon.domain.apply.presentaion.dto.request.ApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyResponse
import com.example.highthon.domain.apply.repository.ApplyRepository
import com.example.highthon.global.common.facade.UserFacade
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
@Transactional(readOnly = true)
class ApplyServiceImpl(
    private val applyRepository: ApplyRepository,
    private val userFacade: UserFacade
): ApplyService {

    @Transactional
    override fun apply(req: ApplyRequest): ApplyResponse {

        val user = userFacade.getCurrentUser()

        if (applyRepository.existsById(user.id)) throw AlreadyAppliedException

        val apply = applyRepository.save(Apply(
            user.id,
            req.motivation!!,
            req.part!!,
            req.githubLink
        ))

        return apply.toResponse(user)
    }
}
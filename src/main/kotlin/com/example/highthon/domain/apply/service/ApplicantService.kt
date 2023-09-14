package com.example.highthon.domain.apply.service

import com.example.highthon.domain.user.entity.type.Part
import com.example.highthon.domain.apply.presentaion.dto.request.ApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.request.EditApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyDetailResponse
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyListResponse
import org.springframework.data.domain.Page
import java.util.UUID

interface ApplicantService {

    fun apply(req: ApplyRequest): ApplyDetailResponse

    fun edit(req: EditApplyRequest): ApplyDetailResponse

    fun cancel(reason: String)

    fun getDetail(id: UUID): ApplyDetailResponse

    fun getListByPart(idx: Int, size: Int, part: Part?): Page<ApplyListResponse>

    fun getCanceledList(idx: Int, size: Int): Page<ApplyListResponse>
}

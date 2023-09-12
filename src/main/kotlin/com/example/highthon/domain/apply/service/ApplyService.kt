package com.example.highthon.domain.apply.service

import com.example.highthon.domain.apply.entity.Apply
import com.example.highthon.domain.apply.entity.Part
import com.example.highthon.domain.apply.presentaion.dto.request.ApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyDetailResponse
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyListResponse
import org.springframework.data.domain.Page
import java.util.UUID

interface ApplyService {

    fun apply(req: ApplyRequest): ApplyDetailResponse

    fun edit(req: ApplyRequest): ApplyDetailResponse

    fun cancel(reason: String)

    fun getDetail(id: UUID): ApplyDetailResponse

    fun getList(idx: Int, size: Int, part: Part): Page<ApplyListResponse>
}

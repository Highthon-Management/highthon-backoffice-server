package com.example.highthon.domain.apply.service

import com.example.highthon.domain.user.entity.type.Part
import com.example.highthon.domain.apply.presentaion.dto.request.ApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.request.EditApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.response.ApplicantDetailResponse
import com.example.highthon.domain.apply.presentaion.dto.response.ApplicantListResponse
import com.example.highthon.domain.user.entity.type.Role
import org.springframework.data.domain.Page
import java.util.UUID

interface ApplicantService {

    fun apply(req: ApplyRequest): ApplicantDetailResponse

    fun edit(req: EditApplyRequest): ApplicantDetailResponse

    fun cancel(reason: String)

    fun getDetail(id: UUID): ApplicantDetailResponse

    fun getCanceledList(idx: Int, size: Int): Page<ApplicantListResponse>

    fun empowerment(id: UUID, role: Role): ApplicantDetailResponse

    fun getListSortedByPartDESC(idx: Int, size: Int): Page<ApplicantListResponse>

    fun getListSortedByPartASC(idx: Int, size: Int): Page<ApplicantListResponse>

    fun getListSortedByGradeDESC(idx: Int, size: Int): Page<ApplicantListResponse>

    fun getListSortedByGradeASC(idx: Int, size: Int): Page<ApplicantListResponse>

    fun getListSortedBySchoolDESC(idx: Int, size: Int): Page<ApplicantListResponse>

    fun getListSortedBySchoolASC(idx: Int, size: Int): Page<ApplicantListResponse>
}

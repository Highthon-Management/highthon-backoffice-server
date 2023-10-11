package com.example.highthon.domain.apply.repository

import com.example.highthon.domain.apply.presentaion.dto.response.ApplicantListResponse
import com.example.highthon.domain.user.entity.type.Role
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable

interface ApplicantRepositoryCustom {

    fun findApplicantSortedBySchoolDESC(pageable: Pageable): Page<ApplicantListResponse>

    fun findApplicantSortedBySchoolASC(pageable: Pageable): Page<ApplicantListResponse>

    fun findApplicantSortedByPartDESC(pageable: Pageable): Page<ApplicantListResponse>

    fun findApplicantSortedByPartASC(pageable: Pageable): Page<ApplicantListResponse>

    fun findApplicantSortedByGradeDESC(pageable: Pageable): Page<ApplicantListResponse>

    fun findApplicantSortedByGradeASC(pageable: Pageable): Page<ApplicantListResponse>

    fun findCanceledAllByUserRole(role: Role, pageable: Pageable): Page<ApplicantListResponse>
}
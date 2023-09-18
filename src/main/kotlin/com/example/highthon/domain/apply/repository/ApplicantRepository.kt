package com.example.highthon.domain.apply.repository

import com.example.highthon.domain.apply.entity.Applicant
import com.example.highthon.domain.user.entity.type.Part
import com.example.highthon.domain.user.entity.type.Role
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ApplicantRepository: JpaRepository<Applicant, UUID> {

    fun findAllByAndUserPartAndIsCanceledAndUserRole(part: Part, isCanceled: Boolean, userRole: Role, pageable: Pageable): Page<Applicant>

    fun findAllByAndSchoolAndIsCanceledAndUserRole(school: String, isCanceled: Boolean, userRole: Role, pageable: Pageable): Page<Applicant>

    fun findAllByAndGradeAndIsCanceledAndUserRole(grade: Int, isCanceled: Boolean, userRole: Role, pageable: Pageable): Page<Applicant>


    fun findAllByIsCanceledAndUserRole(isCanceled: Boolean, userRole: Role, pageable: Pageable): Page<Applicant>
}

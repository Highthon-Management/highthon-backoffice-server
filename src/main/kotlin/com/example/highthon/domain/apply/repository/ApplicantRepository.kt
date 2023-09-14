package com.example.highthon.domain.apply.repository

import com.example.highthon.domain.apply.entity.Applicant
import com.example.highthon.domain.user.entity.type.Part
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ApplicantRepository: JpaRepository<Applicant, UUID> {

    fun findAllByAndUserPartAndIsCanceled(part: Part, isCanceled: Boolean, pageable: Pageable): Page<Applicant>

    fun findAllByIsCanceled(isCanceled: Boolean, pageable: Pageable): Page<Applicant>
}

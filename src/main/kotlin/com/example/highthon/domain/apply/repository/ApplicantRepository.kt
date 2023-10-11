package com.example.highthon.domain.apply.repository

import com.example.highthon.domain.apply.entity.Applicant
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ApplicantRepository: JpaRepository<Applicant, UUID> {

}

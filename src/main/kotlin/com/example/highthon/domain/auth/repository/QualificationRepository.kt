package com.example.highthon.domain.auth.repository

import com.example.highthon.domain.auth.entity.Qualification
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface QualificationRepository: CrudRepository<Qualification, String> {
}
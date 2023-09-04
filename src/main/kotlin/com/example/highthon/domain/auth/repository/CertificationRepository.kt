package com.example.highthon.domain.auth.repository

import com.example.highthon.domain.auth.entity.Certification
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CertificationRepository: CrudRepository<Certification, String> {
}
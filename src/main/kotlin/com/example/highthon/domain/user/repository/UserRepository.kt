package com.example.highthon.domain.user.repository

import com.example.highthon.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
interface UserRepository: JpaRepository<User, Long?> {

    fun findByPhoneNumber(phoneNumber: String): User?

    fun existsByPhoneNumber(phoneNumber: String): Boolean

    fun findByPk(pk: UUID): User?
}
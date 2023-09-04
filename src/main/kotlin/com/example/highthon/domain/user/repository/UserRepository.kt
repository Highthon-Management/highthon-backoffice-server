package com.example.highthon.domain.user.repository

import com.example.highthon.domain.user.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserRepository: JpaRepository<User, Long?> {

    fun findByPhoneNumber(phoneNumber: String): User?
}
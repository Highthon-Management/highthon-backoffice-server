package com.example.highthon.domain.apply.presentaion.dto.response

import com.example.highthon.domain.user.entity.type.Part
import com.example.highthon.domain.user.entity.type.Role
import java.util.*

data class ApplicantDetailResponse (
    val id: UUID,
    val name: String,
    val phoneNumber: String,
    val school: String,
    val motivation: String,
    val part: Part,
    val githubLink: String?,
    val isCanceled: Boolean,
    val reason: String?,
    val bankAccount: String,
    val userRole: Role
)
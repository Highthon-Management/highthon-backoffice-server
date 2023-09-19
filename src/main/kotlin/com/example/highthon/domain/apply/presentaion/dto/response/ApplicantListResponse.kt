package com.example.highthon.domain.apply.presentaion.dto.response

import com.example.highthon.domain.user.entity.type.Part
import java.util.UUID

data class ApplicantListResponse(
    val id: UUID,
    val name: String,
    val school: String,
    val part: Part
)

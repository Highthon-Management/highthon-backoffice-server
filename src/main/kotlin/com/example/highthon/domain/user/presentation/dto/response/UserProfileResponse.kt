package com.example.highthon.domain.user.presentation.dto.response

import com.example.highthon.domain.user.entity.type.Part
import java.util.UUID

data class UserProfileResponse(
    val id: UUID,
    val name: String,
    val grade: Int,
    val part: Part,
    val phoneNumber: String,
    val school: String
)
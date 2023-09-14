package com.example.highthon.domain.user.presentation.dto.response

import com.example.highthon.domain.user.entity.type.Part
import java.util.*

data class UserProfileResponse(
    val id: UUID,
    val name: String,
    val phoneNumber: String,
    val school: String,
    val part: Part
)
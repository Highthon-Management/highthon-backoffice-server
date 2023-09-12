package com.example.highthon.domain.apply.presentaion.dto.response

import com.example.highthon.domain.apply.entity.Part
import java.util.UUID

data class ApplyListResponse(
    val id: UUID,
    val name: String,
    val school: String,
    val part: Part
)

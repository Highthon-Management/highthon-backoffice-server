package com.example.highthon.domain.apply.presentaion.dto.response

import com.example.highthon.domain.apply.entity.Part
import java.util.*

data class ApplyResponse (
    val id: UUID,
    val name: String,
    val phoneNumber: String,
    val grade: Int,
    val school: String,
    val motivation: String,
    val part: Part,
    val githubLink: String?
)
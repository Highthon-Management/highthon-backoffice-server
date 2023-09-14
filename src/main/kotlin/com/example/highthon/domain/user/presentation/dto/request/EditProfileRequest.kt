package com.example.highthon.domain.user.presentation.dto.request

import com.example.highthon.domain.user.entity.type.Part
import javax.validation.constraints.*

data class EditProfileRequest(

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Size(min = 2, max = 4, message = "이름은 2자 ~ 4자입니다.")
    val name: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Size(min = 3, max = 15)
    val school: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    val part: Part?
)

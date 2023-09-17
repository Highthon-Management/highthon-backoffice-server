package com.example.highthon.domain.user.presentation.dto.request

import com.example.highthon.domain.user.entity.type.Part
import javax.validation.constraints.*

data class EditProfileRequest(

    @field:NotBlank(message = "null이 될 수 없습니다.")
    @field:Size(min = 2, max = 4, message = "이름은 2자 ~ 4자입니다.")
    val name: String?,

    @field:NotBlank(message = "null이 될 수 없습니다.")
    @field:Size(min = 3, max = 15)
    val school: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Min(1, message = "학년은 1학년 부터입니다.")
    @field:Max(3, message = "학년은 3학년 부터입니다.")
    val grade: Int?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    val part: Part?
)

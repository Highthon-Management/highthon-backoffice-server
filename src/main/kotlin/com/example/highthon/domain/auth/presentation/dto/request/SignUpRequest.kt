package com.example.highthon.domain.auth.presentation.dto.request

import com.example.highthon.domain.user.entity.type.Part
import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

data class SignUpRequest(

    @field:NotBlank(message = "null이 될 수 없습니다.")
    @field:Size(min = 2, max = 4, message = "이름은 2자 ~ 4자입니다.")
    val name: String?,

    @field:NotBlank(message = "null이 될 수 없습니다.")
    @field:Pattern(
        regexp = "^010\\d{7,8}\$",
        message = "전화번호는 숫자만 넣어야 합니다. (ex)01012345678"
    )
    val phoneNumber: String?,

    @field:NotBlank(message = "null이 될 수 없습니다.")
    @field:Pattern(
        regexp = "^[a-zA-z]{4,}\\d{4,}[!@#%&()*~]",
        message = "비밀번호는 영문 대소문자 4자 이상, 숫자 4자 이상, 특수문자 1자 이상이며 총 15자 이하여야 합니다."
    )
    val password: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    val part: Part,

    @field:NotBlank(message = "null이 될 수 없습니다.")
    @field:Size(min = 3, max = 15)
    val school: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Min(1, message = "학년은 1학년 부터입니다.")
    @field:Max(3, message = "학년은 3학년 부터입니다.")
    val grade: Int?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Max(999999, message = "올바른 인증 번호를 넣어주세요")
    @field:Min(100000, message = "올바른 인증 번호를 넣어주세요")
    val number: Int?
)
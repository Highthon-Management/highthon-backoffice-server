package com.example.highthon.domain.auth.presentation.dto.request

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class ChangePasswordRequest (

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Pattern(
        regexp = "^[a-zA-z]{4,}\\d{4,}[!@#%&\\(\\)\\*~]",
        message = "비밀번호는 영문 대소문자 4자 이상, 숫자 4자 이상, 특수문자 1자 이상이며 총 15자 이하여야 합니다."
    )
    val password: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Max(999999, message = "올바른 인증 번호를 넣어주세요")
    @field:Min(100000, message = "올바른 인증 번호를 넣어주세요")
    val number: Int?
)
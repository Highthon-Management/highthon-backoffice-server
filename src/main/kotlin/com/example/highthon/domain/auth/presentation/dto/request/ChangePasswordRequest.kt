package com.example.highthon.domain.auth.presentation.dto.request

import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class ChangePasswordRequest (

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Pattern(
        regexp = "^[a-zA-z]{4,}\\d{4,}[!@#%&\\(\\)\\*~]",
        message = "비밀번호는 영문 대소문자 4자 이상, 숫자 4자 이상, 특수문자 1자 이상이며 총 15자 이하여야 합니다."
    )
    val oldPassword: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Pattern(
        regexp = "^[a-zA-z]{4,}\\d{4,}[!@#%&\\(\\)\\*~]",
        message = "비밀번호는 영문 대소문자 4자 이상, 숫자 4자 이상, 특수문자 1자 이상이며 총 15자 이하여야 합니다."
    )
    val newPassword: String?
)
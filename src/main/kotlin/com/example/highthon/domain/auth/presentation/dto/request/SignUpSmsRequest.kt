package com.example.highthon.domain.auth.presentation.dto.request

import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class SignUpSmsRequest(

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Pattern(
        regexp = "^010\\d{7,8}\$",
        message = "전화번호는 숫자만 넣어야 합니다. (ex)01012345678"
    )
    val phoneNumber: String?
)

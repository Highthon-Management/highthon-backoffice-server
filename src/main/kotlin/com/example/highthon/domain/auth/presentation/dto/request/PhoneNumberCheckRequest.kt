package com.example.highthon.domain.auth.presentation.dto.request

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class PhoneNumberCheckRequest(

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Pattern(
        regexp = "^010\\d{8}\$",
        message = "전화번호는 숫자만 넣어야 합니다. (ex)01012345678"
    )
    val phoneNumber: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Max(999999, message = "올바른 인증 번호를 넣어주세요")
    @field:Min(100000, message = "올바른 인증 번호를 넣어주세요")
    val number: Int?
)
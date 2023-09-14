package com.example.highthon.domain.auth.presentation.dto.request

import javax.validation.constraints.Max
import javax.validation.constraints.Min
import javax.validation.constraints.NotNull

data class PasswordCheckRequest(

    @field:NotNull(message = "null이 될 수 없습니다.")
    @field:Max(999999, message = "올바른 인증 번호를 넣어주세요")
    @field:Min(100000, message = "올바른 인증 번호를 넣어주세요")
    val number: Int?
)
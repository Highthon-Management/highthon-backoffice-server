package com.example.highthon.domain.apply.presentaion.dto.request

import com.example.highthon.domain.apply.entity.BankType
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class ApplyRequest(

    @field:NotBlank(message = "null이 될 수 없습니다.")
    val motivation: String?,

    @field:Pattern(
        regexp = "^https://github\\.com/.+",
        message = "올바른 깃허브 링크가 아닙니다."
    )
    val githubLink: String?,

    @field:NotBlank(message = "null이 될 수 없습니다.")
    @field:Pattern(
        regexp = "^\\d{2,6}-?\\d{2,6}-?\\d{2,6}-?\\d{2,6}$",
        message = "올바른 계좌 번호가 아닙니다."
    )
    val bankAccount: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    val bankType: BankType?
)

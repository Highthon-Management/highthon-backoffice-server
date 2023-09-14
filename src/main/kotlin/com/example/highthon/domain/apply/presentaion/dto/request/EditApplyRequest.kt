package com.example.highthon.domain.apply.presentaion.dto.request

import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Pattern

data class EditApplyRequest(

    @field:NotBlank(message = "null이 될 수 없습니다.")
    val motivation: String?,

    @field:Pattern(
        regexp = "^https://github\\.com/.+",
        message = "올바른 깃허브 링크가 아닙니다."
    )
    val githubLink: String?,

    @field:NotNull(message = "null이 될 수 없습니다.")
    val isCanceled: Boolean?
)

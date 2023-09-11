package com.example.highthon.domain.apply.presentaion.dto.request

import com.example.highthon.domain.apply.entity.Part
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Pattern

data class ApplyRequest(

    @field:NotBlank(message = "null이 될 수 없습니다.")
    val motivation: String?,

    @field:NotBlank(message = "null이 될 수 없습니다.")
    val part: Part?,

    @field:Pattern(
        regexp = "^https://github\\.com/.+",
        message = "올바른 깃허브 링크가 아닙니다."
    )
    val githubLink: String?
)

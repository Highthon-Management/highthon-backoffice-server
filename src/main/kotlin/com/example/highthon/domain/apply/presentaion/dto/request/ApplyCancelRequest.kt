package com.example.highthon.domain.apply.presentaion.dto.request

import javax.validation.constraints.NotBlank

data class ApplyCancelRequest(

    @field:NotBlank(message = "null이 될 수 없습니다.")
    val reason: String?
)
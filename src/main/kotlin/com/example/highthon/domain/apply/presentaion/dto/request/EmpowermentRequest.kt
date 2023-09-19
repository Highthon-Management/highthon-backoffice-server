package com.example.highthon.domain.apply.presentaion.dto.request

import com.example.highthon.domain.user.entity.type.Role
import javax.validation.constraints.NotNull

data class EmpowermentRequest (

    @field:NotNull(message = "null이 될 수 없습니다.")
    val role: Role?
)
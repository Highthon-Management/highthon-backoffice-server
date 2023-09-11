package com.example.highthon.domain.apply.service

import com.example.highthon.domain.apply.presentaion.dto.request.ApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyResponse

interface ApplyService {

    fun apply(req: ApplyRequest): ApplyResponse

}

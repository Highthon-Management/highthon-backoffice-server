package com.example.highthon.domain.apply.presentaion

import com.example.highthon.domain.apply.presentaion.dto.request.ApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyResponse
import com.example.highthon.domain.apply.service.ApplyService
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/apply")
class ApplyController(
    private val applyService: ApplyService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun apply(
        @RequestBody @Valid
        req: ApplyRequest
    ): ApplyResponse = applyService.apply(req)

    @PutMapping
    fun edit(
        @RequestBody @Valid
        req: ApplyRequest
    ): ApplyResponse = applyService.edit(req)

    @DeleteMapping
    fun cancel() {
        applyService.cancel()
    }
}
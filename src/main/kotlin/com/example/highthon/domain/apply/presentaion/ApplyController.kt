package com.example.highthon.domain.apply.presentaion

import com.example.highthon.domain.apply.entity.Part
import com.example.highthon.domain.apply.presentaion.dto.request.ApplyCancelRequest
import com.example.highthon.domain.apply.presentaion.dto.request.ApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyDetailResponse
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyListResponse
import com.example.highthon.domain.apply.service.ApplyService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
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
    ): ApplyDetailResponse = applyService.apply(req)

    @PutMapping
    fun edit(
        @RequestBody @Valid
        req: ApplyRequest
    ): ApplyDetailResponse = applyService.edit(req)

    @DeleteMapping
    fun cancel(
        @RequestBody @Valid
        req: ApplyCancelRequest
    ) {
        applyService.cancel(req.reason!!)
    }

    @GetMapping("/detail")
    fun getApplyDetail(
        @RequestParam("id") id: UUID
    ): ApplyDetailResponse = applyService.getDetail(id)


    @GetMapping("/list")
    fun getApplyList(
        @RequestParam("idx") idx: Int = 0,
        @RequestParam("size") size: Int = 5,
        @RequestParam("part") part: Part
    ): Page<ApplyListResponse> = applyService.getList(idx, size, part)

}
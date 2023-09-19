package com.example.highthon.domain.apply.presentaion

import com.example.highthon.domain.user.entity.type.Part
import com.example.highthon.domain.apply.presentaion.dto.request.ApplyCancelRequest
import com.example.highthon.domain.apply.presentaion.dto.request.ApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.request.EditApplyRequest
import com.example.highthon.domain.apply.presentaion.dto.request.EmpowermentRequest
import com.example.highthon.domain.apply.presentaion.dto.response.ApplicantDetailResponse
import com.example.highthon.domain.apply.presentaion.dto.response.ApplicantListResponse
import com.example.highthon.domain.apply.service.ApplicantService
import org.springframework.data.domain.Page
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import java.util.*
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/apply")
class ApplicantController(
    private val applicantService: ApplicantService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun apply(
        @RequestBody @Valid
        req: ApplyRequest
    ): ApplicantDetailResponse = applicantService.apply(req)

    @PutMapping
    fun edit(
        @RequestBody @Valid
        req: EditApplyRequest
    ): ApplicantDetailResponse = applicantService.edit(req)

    @DeleteMapping
    fun cancel(
        @RequestBody @Valid
        req: ApplyCancelRequest
    ) {
        applicantService.cancel(req.reason!!)
    }

    @GetMapping("/detail")
    fun getApplicantDetail(
        @RequestParam("id") id: UUID
    ): ApplicantDetailResponse = applicantService.getDetail(id)


    @GetMapping("/list/{part}")
    fun getApplicantListByPart(
        @RequestParam("idx", required = true) idx: Int = 0,
        @RequestParam("size", required = true) size: Int = 5,
        @PathVariable("part") part: Part
    ): Page<ApplicantListResponse> = applicantService.getListByPart(idx, size, part)

    @GetMapping("/list/{school}")
    fun getApplicantListBySchool(
        @RequestParam("idx", required = true) idx: Int = 0,
        @RequestParam("size", required = true) size: Int = 5,
        @PathVariable("school") school: String
    ): Page<ApplyListResponse> = applicantService.getListBySchool(idx, size, school)

    @GetMapping("/list/{grade}")
    fun getApplicantListByGrade(
        @RequestParam("idx", required = true) idx: Int = 0,
        @RequestParam("size", required = true) size: Int = 5,
        @PathVariable("grade") grade: Int
    ): Page<ApplyListResponse> = applicantService.getListByGrade(idx, size, grade)

    @GetMapping("/list")
    fun getApplyList(
        @RequestParam("idx", required = true) idx: Int = 0,
        @RequestParam("size", required = true) size: Int = 5
    ): Page<ApplicantListResponse> = applicantService.getListByPart(idx, size)

    @GetMapping("/list/cancel")
    fun getCanceledApplicant(
        @RequestParam("idx", required = true) idx: Int = 0,
        @RequestParam("size", required = true) size: Int = 5
    ): Page<ApplicantListResponse> = applicantService.getCanceledList(idx, size)

    @PostMapping("/empowerment")
    fun empowermentApplicant(
        @RequestParam("id", required = true) id: UUID,
        @RequestBody @Valid
        req: EmpowermentRequest
    ): ApplicantDetailResponse = applicantService.empowerment(id, req.role!!)
}
package com.example.highthon.domain.submit.presentation

import com.example.highthon.domain.submit.service.SubmitService
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile

@RestController
@RequestMapping("/submit")
class SubmitController(
    private val submitService: SubmitService
) {

    @PostMapping("/upload")
    fun uploadMultipleFile(
        @RequestPart("file", required = true) file: MultipartFile,
        @RequestParam("team", required = true) team: String
    ) = submitService.upload(file, team)

    @PostMapping("/upload/files")
    fun uploadMultipleFiles(
        @RequestPart("files", required = true) files: List<MultipartFile>,
        @RequestParam("team", required = true) team: String
    ) = submitService.uploads(files, team)

    @GetMapping
    fun getFilesByTeam(
        @RequestParam("team", required = true) team: String
    ) = submitService.getFilesByTeam(team)
}
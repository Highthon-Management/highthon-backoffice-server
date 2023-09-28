package com.example.highthon.domain.submit.service

import com.example.highthon.domain.submit.presentation.dto.response.FileUrlListResponse
import com.example.highthon.domain.submit.presentation.dto.response.FileUrlResponse
import org.springframework.web.multipart.MultipartFile

interface SubmitService {

    fun upload(file: MultipartFile, team: String): FileUrlResponse

    fun uploads(file: List<MultipartFile>, team: String): FileUrlListResponse

    fun getFilesByTeam(team: String): FileUrlListResponse
}
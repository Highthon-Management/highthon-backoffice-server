package com.example.highthon.domain.submit.service

import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ListObjectsRequest
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import com.amazonaws.util.IOUtils
import com.example.highthon.domain.apply.exception.PermissionDeniedException
import com.example.highthon.domain.submit.entity.type.FileType
import com.example.highthon.domain.submit.exception.BadTeamNameException
import com.example.highthon.domain.submit.exception.InvalidFileExtension
import com.example.highthon.domain.submit.exception.TimeUpException
import com.example.highthon.domain.submit.presentation.dto.response.FileUrlListResponse
import com.example.highthon.domain.submit.presentation.dto.response.FileUrlResponse
import com.example.highthon.domain.user.entity.type.Role
import com.example.highthon.global.common.facade.UserFacade
import com.example.highthon.global.env.s3.S3Property
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import org.springframework.web.multipart.MultipartFile
import java.io.ByteArrayInputStream
import java.time.LocalDateTime
import java.time.Month
import java.util.*

@Service
@Transactional(readOnly = true)
class SubmitServiceImpl(
    private val s3Client: AmazonS3Client,
    private val s3Property: S3Property,
    private val userFacade: UserFacade
): SubmitService {

    private companion object {
        const val URL_PREFIX = "https://%s.s3.%s.amazonaws.com/%s"
        val DEAD_LINE: LocalDateTime = LocalDateTime.of(2024, Month.JULY, 21, 11, 0, 0)
    }

    private fun uploadFile(file: MultipartFile, teamName: String): FileUrlResponse {

        val bytes: ByteArray = IOUtils.toByteArray(file.inputStream)

        val objectMetadata = ObjectMetadata().apply {
            this.contentType = file.contentType
            this.contentLength = bytes.size.toLong()
        }

        var fileName: String = file.originalFilename ?: file.name


        val ext = fileName.split('.').last()

        try { FileType.values().first { it.extension ==  ext } } catch (e: NoSuchElementException) {throw InvalidFileExtension}

        fileName = s3Property.dir + "$teamName/" + fileName

        val putObjectRequest = PutObjectRequest(
            s3Property.bucket,
            fileName,
            ByteArrayInputStream(bytes),
            objectMetadata,
        )

        s3Client.putObject(putObjectRequest)

        return FileUrlResponse(URL_PREFIX.format(
                s3Property.bucket,
                s3Property.region,
                fileName
            )
        )
    }

    private fun validate(team: String) {

        team.takeIf { !it.contains('/') } ?: throw BadTeamNameException

        userFacade.getCurrentUser().takeIf { it.role == Role.PARTICIPANT }
            ?: throw PermissionDeniedException

        if (LocalDateTime.now().isAfter(DEAD_LINE)) throw TimeUpException
    }

    override fun upload(file: MultipartFile, team: String): FileUrlResponse {

        validate(team)

        return uploadFile(file, team)
    }

    override fun uploads(file: List<MultipartFile>, team: String): FileUrlListResponse {

        validate(team)

        return FileUrlListResponse(
            file.map {
                uploadFile(it, team)
            }.toMutableList()
        )
    }

    override fun getFilesByTeam(team: String) = FileUrlListResponse(

        s3Client.listObjects(ListObjectsRequest().apply {
            bucketName = s3Property.bucket
            prefix = "${s3Property.dir}$team/"
        }).objectSummaries.map {
            FileUrlResponse(
                URL_PREFIX.format(
                    s3Property.bucket,
                    s3Property.region,
                    it.key
                )
            )
        }.toMutableList()
    )
}
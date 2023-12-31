package com.example.highthon.domain.user.presentation

import com.example.highthon.domain.user.presentation.dto.request.EditProfileRequest
import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse
import com.example.highthon.domain.user.service.UserService
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@Validated
@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/me")
    fun getProfile(): UserProfileResponse = userService.getProfile()

    @PutMapping("/edit")
    fun editProfile(
        @RequestBody @Valid
        req: EditProfileRequest
    ): UserProfileResponse = userService.editProfile(req)
}
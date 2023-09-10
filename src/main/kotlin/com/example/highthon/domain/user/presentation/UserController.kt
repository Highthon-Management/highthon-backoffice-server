package com.example.highthon.domain.user.presentation

import com.example.highthon.domain.user.entity.User
import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse
import com.example.highthon.domain.user.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/user")
class UserController(
    private val userService: UserService
) {

    @GetMapping("/me")
    fun getProfile(): UserProfileResponse = userService.getProfile()
}
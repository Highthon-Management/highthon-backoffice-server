package com.example.highthon.domain.auth.presentation

import com.example.highthon.domain.auth.presentation.dto.request.*
import com.example.highthon.domain.auth.presentation.dto.response.TokenResponse
import com.example.highthon.domain.auth.service.AuthService
import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val authService: AuthService
) {

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.CREATED)
    fun login(
        @RequestBody @Valid
        req: LoginRequest
    ): TokenResponse = authService.login(req)

    @PatchMapping("/change/phone-number")
    fun changePhoneNumber(
        @RequestBody @Valid
        req: ChangePhoneNumberRequest
    ): UserProfileResponse = authService.changePhoneNumber(req)

    @PatchMapping("/change/password")
    fun changePassword(
        @RequestBody @Valid
        req: ChangePasswordRequest
    ): UserProfileResponse = authService.changePassword(req)

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(
        @RequestBody @Valid
        request: SignUpRequest
    ) {
        authService.signup(request)
    }

    @PostMapping("/reissue")
    @ResponseStatus(HttpStatus.CREATED)
    fun reissue(
        @RequestBody @Valid
        request: ReissueRequest
    ): TokenResponse = authService.reissue(request)
}

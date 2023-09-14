package com.example.highthon.domain.auth.presentation

import com.example.highthon.domain.auth.entity.type.NumberType
import com.example.highthon.domain.auth.presentation.dto.request.CheckNumberRequest
import com.example.highthon.domain.auth.presentation.dto.request.LoginRequest
import com.example.highthon.domain.auth.presentation.dto.request.SMSRequest
import com.example.highthon.domain.auth.presentation.dto.request.SignUpRequest
import com.example.highthon.domain.auth.presentation.dto.response.TokenResponse
import com.example.highthon.domain.auth.service.AuthService
import com.example.highthon.domain.auth.service.SMSService
import com.example.highthon.global.config.jwt.TokenProvider
import net.nurigo.sdk.message.response.SingleMessageSentResponse
import org.springframework.http.HttpStatus
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/auth")
@Validated
class AuthController(
    private val smsService: SMSService,
    private val authService: AuthService,
    private val tokenProvider: TokenProvider
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


    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(@RequestBody request: SignUpRequest){
        val phoneNumber = request.phoneNumber?: ""
        val number = request.number?:0
        authService.signup(request,phoneNumber,number)
    }

    @PostMapping("/reissue")
    fun reissueToken(
        @RequestBody @Valid

    )


}
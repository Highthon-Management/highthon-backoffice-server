package com.example.highthon.domain.user.service

import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse

interface UserService {

    fun getProfile(): UserProfileResponse

}

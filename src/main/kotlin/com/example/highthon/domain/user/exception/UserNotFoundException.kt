package com.example.highthon.domain.user.exception

import com.example.highthon.global.config.error.data.ErrorCode
import com.example.highthon.global.config.error.exception.BusinessException

object UserNotFoundException : BusinessException(ErrorCode.USER_NOT_FOUND)

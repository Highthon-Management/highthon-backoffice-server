package com.example.highthon.domain.apply.exception

import com.example.highthon.global.config.error.data.ErrorCode
import com.example.highthon.global.config.error.exception.BusinessException

object AlreadyCanceledApplyException: BusinessException(ErrorCode.ALREADY_CANCELED_APPLY)
package com.example.highthon.domain.auth.exception

import com.example.highthon.global.config.error.data.ErrorCode
import com.example.highthon.global.config.error.exception.BusinessException

object NumberNotMatchedException: BusinessException(ErrorCode.NUMBER_NOT_MATCHED)
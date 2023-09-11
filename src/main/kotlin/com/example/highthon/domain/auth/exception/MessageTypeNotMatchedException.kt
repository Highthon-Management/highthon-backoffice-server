package com.example.highthon.domain.auth.exception

import com.example.highthon.global.config.error.data.ErrorCode
import com.example.highthon.global.config.error.exception.BusinessException

object MessageTypeNotMatchedException: BusinessException(ErrorCode.MESSAGE_TYPE_NOT_MATCHED)
package com.example.highthon.domain.submit.exception

import com.example.highthon.global.config.error.data.ErrorCode
import com.example.highthon.global.config.error.exception.BusinessException

object TimeUpException : BusinessException(ErrorCode.TIMES_UP)
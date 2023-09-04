package com.example.highthon.global.config.error.exception

import com.example.highthon.global.config.error.data.ErrorCode


open class BusinessException(val errorCode: ErrorCode): RuntimeException(errorCode.message)
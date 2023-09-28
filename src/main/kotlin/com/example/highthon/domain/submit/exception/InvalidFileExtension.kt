package com.example.highthon.domain.submit.exception

import com.example.highthon.global.config.error.data.ErrorCode
import com.example.highthon.global.config.error.exception.BusinessException

object InvalidFileExtension : BusinessException(ErrorCode.INVALID_FILE)

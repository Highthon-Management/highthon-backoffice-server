package com.example.highthon.domain.apply.exception

import com.example.highthon.global.config.error.data.ErrorCode
import com.example.highthon.global.config.error.exception.BusinessException

object ApplyNotFoundException : BusinessException(ErrorCode.APPLY_NOT_FOUND)

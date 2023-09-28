package com.example.highthon.domain.submit.exception

import com.example.highthon.global.config.error.data.ErrorCode
import com.example.highthon.global.config.error.exception.BusinessException

object BadTeamNameException : BusinessException(ErrorCode.BAD_TEAM_NAME)

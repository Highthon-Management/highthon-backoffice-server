package com.example.highthon.global.config.error.data

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String
) {

    // 400
    PASSWORD_NOT_MATCHED(HttpStatus.BAD_REQUEST, "패스워드가 일치하지 않습니다."),
    MESSAGE_TYPE_NOT_MATCHED(HttpStatus.BAD_REQUEST, "메세지 타입이 일치하지 않습니다."),
    PHONE_NUMBER_MATCHED(HttpStatus.BAD_REQUEST, "같은 전화번호 입니다."),

    // 401
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다."),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다."),

    //403
    PERMISSION_DENIED(HttpStatus.FORBIDDEN, "권한이 거부되었습니다."),

    // 404
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "유저를 찾을 수 없습니다."),
    APPLY_NOT_FOUND(HttpStatus.NOT_FOUND, "신청을 한 적이 없습니다."),

    // 409
    ALREADY_POSTED_MESSAGE(HttpStatus.CONFLICT, "이미 문자 요청을 보냈습니다."),
    ALREADY_SIGN_UP(HttpStatus.CONFLICT, "이미 가입한 전화번호 입니다."),
    ALREADY_APPLIED(HttpStatus.CONFLICT, "이미 지원했습니다."),
    ALREADY_CANCELED_APPLY(HttpStatus.CONFLICT, "이미 취소했습니다."),
    MESSAGE_NOT_SENT_YET(HttpStatus.CONFLICT, "아직 문자가 발송되지 않았습니다."),

    // 500
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "서버 내부 에러"),
}

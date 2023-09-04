package com.example.highthon.domain.auth.entity

import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed

@RedisHash(timeToLive = 300) // 5분
data class Certification(

    @Id
    var requester: String,

    @Indexed
    var certificationNumber: Int
)
package com.example.highthon.domain.auth.entity

import com.example.highthon.domain.auth.entity.type.NumberType
import org.springframework.data.annotation.Id
import org.springframework.data.redis.core.RedisHash
import org.springframework.data.redis.core.index.Indexed
import javax.persistence.EnumType
import javax.persistence.Enumerated

@RedisHash(timeToLive = 300) // 5분
data class Qualification(

    @Id
    var id: String,

    @Indexed
    var number: Int,

    @Indexed
    @Enumerated(EnumType.STRING)
    var type: NumberType
)
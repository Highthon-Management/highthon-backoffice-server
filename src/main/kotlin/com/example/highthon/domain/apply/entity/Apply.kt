package com.example.highthon.domain.apply.entity

import com.example.highthon.domain.apply.presentaion.dto.response.ApplyDetailResponse
import com.example.highthon.domain.apply.presentaion.dto.response.ApplyListResponse
import com.example.highthon.domain.user.entity.User
import org.hibernate.annotations.DynamicUpdate
import java.util.*
import javax.persistence.*

@Entity(name = "apply")
@DynamicUpdate
class Apply(
    user: User,
    motivation: String,
    github: String?,
    isCanceled: Boolean? = null,
    reason: String? = null
) {

    @Id
    @Column(name = "id")
    var id: UUID = user.id!!
        protected set

    @OneToOne(mappedBy = "user_id")
    @MapsId
    @JoinColumn(name = "id", columnDefinition = "BINARY(16)")
    var user: User = user
        protected set

    @Column(name = "motivation", columnDefinition = "VARCHAR(1000)", nullable = false)
    var motivation: String = motivation
        protected set

    @Column(name = "github_link", columnDefinition = "VARCHAR(30)")
    var github: String? = github
        protected set

    @Column(name = "is_canceled", columnDefinition = "BIT", nullable = false)
    var isCanceled: Boolean = isCanceled ?: false
        protected set

    @Column(name = "reason", columnDefinition = "VARCHAR(1000)")
    var reason: String? = reason
        protected set

    fun toResponse() = ApplyDetailResponse(
        this.id,
        this.user.name,
        this.user.phoneNumber,
        this.user.school,
        this.motivation,
        this.user.part,
        this.github,
    )

    fun toMinimumResponse() = ApplyListResponse(
        this.id,
        this.user.name,
        this.user.school,
        this.user.part
    )
}
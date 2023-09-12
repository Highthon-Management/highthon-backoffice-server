package com.example.highthon.domain.apply.entity

import com.example.highthon.domain.apply.presentaion.dto.response.ApplyResponse
import com.example.highthon.domain.user.entity.User
import org.hibernate.annotations.DynamicUpdate
import java.util.*
import javax.persistence.*

@Entity(name = "apply")
@DynamicUpdate
class Apply(
    id: UUID,
    motivation: String,
    part: Part,
    github: String?,
    isCanceled: Boolean? = null,
    reason: String? = null
) {

    @Id @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false)
    var id: UUID = id
        protected set

    @Column(name = "motivation", columnDefinition = "VARCHAR(1000)", nullable = false)
    var motivation: String = motivation
        protected set

    @Column(name = "part", columnDefinition = "VARCHAR(9)", nullable = false)
    @Enumerated(EnumType.STRING)
    var part: Part = part
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

    fun toResponse(user: User) = ApplyResponse(
        id = this.id,
        name = user.name,
        phoneNumber = user.phoneNumber,
        grade = user.grade,
        school = user.school,
        motivation = this.motivation,
        part = this.part,
        githubLink = this.github,
        isCanceled = this.isCanceled,
        reason = this.reason
    )

}
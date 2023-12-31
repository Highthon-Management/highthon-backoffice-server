package com.example.highthon.domain.user.entity

import com.example.highthon.domain.user.entity.type.Part
import com.example.highthon.domain.user.entity.type.Role
import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse
import org.hibernate.annotations.DynamicUpdate
import java.util.*
import javax.persistence.*

@Entity(name = "user")
@DynamicUpdate
class User(
    id: UUID? = null,
    name: String,
    phoneNumber: String,
    password: String,
    school: String,
    grade: Int,
    part: Part,
    role: Role
) {

    @Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", columnDefinition = "BINARY(16)")
    var id: UUID? = id
        protected set

    @Column(name = "name",  columnDefinition = "CHAR(4)", nullable = false)
    var name: String = name
        protected set

    @Column(name = "phone_number", columnDefinition = "CHAR(11)", nullable = false, unique = true)
    var phoneNumber: String = phoneNumber
        protected set

    @Column(name = "password", columnDefinition = "CHAR(60)", nullable = false)
    var password: String = password
        protected set

    @Column(name = "school", length = 15, nullable = false)
    var school: String = school
        protected set

    @Column(name = "grade", nullable = false)
    var grade: Int = grade
        protected set

    @Column(name = "part", length = 9, nullable = false)
    @Enumerated(EnumType.STRING)
    var part: Part = part
        protected set

    @Column(name = "role", length = 11, nullable = false)
    @Enumerated(value = EnumType.STRING)
    var role: Role = role
        protected set

    fun toResponse() = UserProfileResponse(
        this.id!!,
        this.name,
        this.phoneNumber,
        this.school,
        this.part
    )
}
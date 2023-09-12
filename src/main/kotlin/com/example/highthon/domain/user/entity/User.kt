package com.example.highthon.domain.user.entity

import com.example.highthon.domain.user.entity.type.Role
import com.example.highthon.domain.user.presentation.dto.response.UserProfileResponse
import org.hibernate.annotations.DynamicUpdate
import java.util.*
import javax.persistence.*

@Entity(name = "user")
@DynamicUpdate
class User(
    id: Long? = null,
    pk: UUID? = null,
    name: String,
    phoneNumber: String,
    password: String,
    grade: Int,
    school: String,
    role: Role
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    var id: Long? = id
        protected set

    @Column(name = "pk", columnDefinition = "BINARY(16)", nullable = false, unique = true)
    var pk: UUID? = pk ?: UUID.randomUUID()
        protected set

    @Column(name = "name",  columnDefinition = "CHAR(4)", nullable = false)
    var name: String = name
        protected set

    @Column(name = "phone_number", columnDefinition = "CHAR(11)", nullable = false, unique = true)
    var phoneNumber: String = phoneNumber
        protected set

    @Column(name = "school", columnDefinition = "VARCHAR(15)", nullable = false)
    var school: String = school

    @Column(name = "password", columnDefinition = "CHAR(60)", nullable = false)
    var password: String = password
        protected set

    @Column(name = "grade", nullable = false)
    var grade: Int = grade
        protected set

    @Column(name = "role", columnDefinition = "VARCHAR(11)", nullable = false)
    @Enumerated(value = EnumType.STRING)
    var role: Role = role
        protected set

    fun toResponse() = UserProfileResponse(
        this.pk!!,
        this.name,
        this.grade,
        this.phoneNumber,
        this.school
    )
}
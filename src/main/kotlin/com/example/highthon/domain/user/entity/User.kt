package com.example.highthon.domain.user.entity

import com.example.highthon.domain.user.entity.type.Part
import com.example.highthon.domain.user.entity.type.Role
import org.hibernate.annotations.DynamicUpdate
import java.util.*
import javax.persistence.*
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Entity(name = "user")
@DynamicUpdate
class User(
    pk: Long? = null,
    id: UUID? = null,
    name: String,
    phoneNumber: String,
    school: String,
    part: Part,
    password: String,
    grade: Int,
    role: Role
) {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pk", nullable = false)
    var pk: Long? = pk
        protected set

    @Column(name = "id", columnDefinition = "BINARY(16)", nullable = false, unique = true)
    var id: UUID = id ?: UUID.randomUUID()
        protected set

    @Column(name = "name",  columnDefinition = "CHAR(4)", nullable = false)
    var name: String = name
        protected set

    @Column(name = "phone_number", columnDefinition = "CHAR(11)", nullable = false, unique = true)
    var phoneNumber: String = phoneNumber
        protected set

    @Column(name = "school", columnDefinition = "VARCHAR(15)", nullable = false)
    var school: String = school

    @Column(name = "part", columnDefinition = "VARCHAR(9)", nullable = false)
    @Enumerated(EnumType.STRING)
    var part: Part = part
        protected set

    @Column(name = "password", columnDefinition = "CHAR(60)", nullable = false)
    var password: String = password
        protected set

    @Min(1, message = "학년은 1이상 이여야 합니다.")
    @Max(3, message = "학년은 3하 이여야 합니다.")
    @Column(name = "grade", nullable = false)
    var grade: Int = grade
        protected set

    @Column(name = "role", columnDefinition = "VARCHAR(11)", nullable = false)
    @Enumerated(EnumType.STRING)
    var role: Role = role
        protected set
}
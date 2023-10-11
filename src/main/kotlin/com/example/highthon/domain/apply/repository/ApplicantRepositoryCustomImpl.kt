package com.example.highthon.domain.apply.repository

import com.example.highthon.domain.apply.entity.QApplicant.applicant
import com.example.highthon.domain.apply.presentaion.dto.response.ApplicantListResponse
import com.example.highthon.domain.user.entity.QUser.user
import com.example.highthon.domain.user.entity.type.Role
import com.querydsl.core.types.Projections
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class ApplicantRepositoryCustomImpl(
    private val queryFactory: JPAQueryFactory
): ApplicantRepositoryCustom {

    override fun findApplicantSortedByPartDESC(pageable: Pageable): Page<ApplicantListResponse> {

        val query = queryFactory.select(
            Projections.constructor(
                ApplicantListResponse::class.java,
                applicant.id,
                user.name,
                user.school,
                user.grade,
                user.part
            ))
            .from(applicant)
            .innerJoin(applicant.user, user)
            .where(applicant.isCanceled.eq(false))
            .orderBy(user.part.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        val applicants = query.fetch()

        return PageImpl(applicants, pageable, applicants.size.toLong())
    }

    override fun findApplicantSortedByPartASC(pageable: Pageable): Page<ApplicantListResponse> {

        val query = queryFactory.select(
            Projections.constructor(
                ApplicantListResponse::class.java,
                applicant.id,
                user.name,
                user.school,
                user.grade,
                user.part
            ))
            .from(applicant)
            .innerJoin(applicant.user, user)
            .where(applicant.isCanceled.eq(false))
            .orderBy(user.part.asc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong())

        val applicants = query.fetch()

        return PageImpl(applicants, pageable, applicants.size.toLong())
    }

    override fun findApplicantSortedByGradeDESC(pageable: Pageable): Page<ApplicantListResponse> {

        val applicants = queryFactory.select(
            Projections.constructor(
                ApplicantListResponse::class.java,
                applicant.id,
                user.name,
                user.school,
                user.grade,
                user.part
            ))
            .from(applicant)
            .innerJoin(applicant.user, user)
            .where(applicant.isCanceled.eq(false))
            .orderBy(user.grade.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong()).fetch()

        return PageImpl(applicants, pageable, applicants.size.toLong())
    }

    override fun findApplicantSortedByGradeASC(pageable: Pageable): Page<ApplicantListResponse> {

        val applicants = queryFactory.select(
            Projections.constructor(
                ApplicantListResponse::class.java,
                applicant.id,
                user.name,
                user.school,
                user.grade,
                user.part
            ))
            .from(applicant)
            .innerJoin(applicant.user, user)
            .where(applicant.isCanceled.eq(false))
            .orderBy(user.grade.asc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong()).fetch()

        return PageImpl(applicants, pageable, applicants.size.toLong())
    }

    override fun findApplicantSortedBySchoolDESC(pageable: Pageable): Page<ApplicantListResponse> {

        val applicants: List<ApplicantListResponse> = queryFactory.select(
            Projections.constructor(
                ApplicantListResponse::class.java,
                applicant.id,
                user.name,
                user.school,
                user.grade,
                user.part
            ))
            .from(applicant)
            .innerJoin(applicant.user, user)
            .where(applicant.isCanceled.eq(false))
            .orderBy(user.school.desc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong()).fetch()

        return PageImpl(applicants, pageable, applicants.size.toLong())
    }

    override fun findApplicantSortedBySchoolASC(pageable: Pageable): Page<ApplicantListResponse> {

        val applicants: List<ApplicantListResponse> = queryFactory.select(
            Projections.constructor(
                ApplicantListResponse::class.java,
                applicant.id,
                user.name,
                user.school,
                user.grade,
                user.part
            ))
            .from(applicant)
            .innerJoin(applicant.user, user)
            .where(applicant.isCanceled.eq(false))
            .orderBy(user.school.asc())
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong()).fetch()

        return PageImpl(applicants, pageable, applicants.size.toLong())
    }

    override fun findCanceledAllByUserRole(role: Role, pageable: Pageable): Page<ApplicantListResponse> {

        val applicants: List<ApplicantListResponse> = queryFactory.select(
            Projections.constructor(
                ApplicantListResponse::class.java,
                applicant.id,
                user.name,
                user.school,
                user.grade,
                user.part
            ))
            .from(applicant)
            .innerJoin(applicant.user, user)
            .where(applicant.isCanceled.eq(true), user.role.eq(role))
            .offset(pageable.offset)
            .limit(pageable.pageSize.toLong()).fetch()

        return PageImpl(applicants, pageable, applicants.size.toLong())
    }
}
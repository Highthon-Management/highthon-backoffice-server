package com.example.highthon.domain.apply.repository

import com.example.highthon.domain.apply.entity.Apply
import com.example.highthon.domain.apply.entity.Part
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ApplyRepository: JpaRepository<Apply, UUID> {

    fun findAllByAndUserPart(part: Part, pageable: Pageable): Page<Apply>
}

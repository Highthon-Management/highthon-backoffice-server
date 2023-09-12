package com.example.highthon.domain.apply.repository

import com.example.highthon.domain.apply.entity.Apply
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ApplyRepository: JpaRepository<Apply, UUID> {

    fun findByIdAndIsCanceled(id: UUID, isCanceled: Boolean): Apply?
}

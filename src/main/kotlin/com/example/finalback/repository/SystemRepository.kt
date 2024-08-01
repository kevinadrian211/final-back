package com.example.finalback.repository

import com.example.finalback.entity.SystemEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface SystemRepository : JpaRepository<SystemEntity, Long>
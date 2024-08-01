package com.example.finalback.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import java.time.LocalTime

@Entity
@Table(name = "system")
class SystemEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(updatable = false)
    var id: Long? = null

    @Column(nullable = false)
    var named: String? = null

    @Column(nullable = false)
    var stated: Boolean = false

    @Column(nullable = false)
    var intensity: Int = 0

    @Column(nullable = false)
    var ontime: LocalTime? = null

    @Column(nullable = false)
    var offtime: LocalTime? = null

    @Column
    var lastSensorValue: Double? = null

    @Column
    var lastSensorRecord: LocalDateTime? = null
}
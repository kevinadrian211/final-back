package com.example.finalback.service

import com.example.finalback.entity.SystemEntity
import com.example.finalback.repository.SystemRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.time.LocalDateTime
import java.time.LocalTime

@Service
class SystemService {

    @Autowired
    private lateinit var systemRepository: SystemRepository

    fun getSystem(id: Long): SystemEntity? {
        return systemRepository.findById(id).orElse(null)
    }

    fun createOrUpdateSystem(system: SystemEntity): SystemEntity {
        return systemRepository.save(system)
    }

    fun deleteSystem(id: Long) {
        systemRepository.deleteById(id)
    }

    fun updateSensorData(id: Long, value: Double, timestamp: LocalDateTime) {
        val system = systemRepository.findById(id).orElse(null)
        system?.let {
            it.lastSensorValue = value
            it.lastSensorRecord = timestamp
            systemRepository.save(it)
        }
    }

    fun turnOnSystem(id: Long, onTime: LocalTime, offTime: LocalTime): SystemEntity? {
        val system = systemRepository.findById(id).orElse(null)
        system?.let {
            it.stated = true
            it.ontime = onTime
            it.offtime = offTime
            return systemRepository.save(it)
        }
        return null
    }



    fun turnOffSystem(id: Long): SystemEntity? {
        val system = systemRepository.findById(id).orElse(null)
        system?.let {
            it.stated = false
            it.intensity = 0
            it.ontime = LocalTime.MIDNIGHT  // O cualquier otro valor válido
            it.offtime = LocalTime.MIDNIGHT // O cualquier otro valor válido
            return systemRepository.save(it)
        }
        return null
    }



    fun updateSystemTime(id: Long, ontime: LocalTime, offtime: LocalTime): SystemEntity? {
        val system = systemRepository.findById(id).orElse(null)
        system?.let {
            it.ontime = ontime
            it.offtime = offtime
            return systemRepository.save(it)
        }
        return null
    }

    fun updateIntensity(id: Long, intensity: Int): SystemEntity? {
        val system = systemRepository.findById(id).orElse(null)
        system?.let {
            it.intensity = intensity
            return systemRepository.save(it)
        }
        return null
    }

}

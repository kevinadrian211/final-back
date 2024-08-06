package com.example.finalback.controller

import com.example.finalback.entity.SystemEntity
import com.example.finalback.service.SystemService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@RestController
@RequestMapping("/system")
class SystemController {

    @Autowired
    private lateinit var systemService: SystemService

    @GetMapping("/{id}")
    fun getSystem(@PathVariable id: Long): ResponseEntity<SystemEntity> {
        val system = systemService.getSystem(id)
        return if (system != null) {
            ResponseEntity(system, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PostMapping
    fun createOrUpdateSystem(@RequestBody system: SystemEntity): ResponseEntity<SystemEntity> {
        val updatedSystem = systemService.createOrUpdateSystem(system)
        return ResponseEntity(updatedSystem, HttpStatus.OK)
    }

    @DeleteMapping("/{id}")
    fun deleteSystem(@PathVariable id: Long): ResponseEntity<String> {
        systemService.deleteSystem(id)
        return ResponseEntity.ok("System deleted successfully")
    }

    @PostMapping("/sensor")
    fun updateSensorData(@RequestBody sensorData: Map<String, Any>): ResponseEntity<String> {
        println("Received sensorData: $sensorData") // Registro de los datos recibidos

        val systemId = (sensorData["systemId"] as? Number)?.toLong() ?: return ResponseEntity.badRequest().body("Missing or invalid 'systemId'")
        val value = (sensorData["intensity"] as? Number)?.toDouble() ?: return ResponseEntity.badRequest().body("Missing or invalid 'intensity'")
        val timestamp = sensorData["timestamp"]?.toString() ?: return ResponseEntity.badRequest().body("Missing 'timestamp'")

        println("systemId: $systemId, intensity: $value, timestamp: $timestamp") // Registro de los datos procesados

        val formatter = DateTimeFormatter.ISO_DATE_TIME
        val localDateTime = LocalDateTime.parse(timestamp, formatter)

        println("localDateTime: $localDateTime") // Registro de la conversión del tiempo

        // Aquí asumo que systemService.updateSensorData está correctamente implementado.
        systemService.updateSensorData(systemId, value, localDateTime)

        return ResponseEntity.ok("Sensor data updated successfully")
    }

    @PutMapping("/{id}/turnOn")
    fun turnOnSystem(
        @PathVariable id: Long,
        @RequestBody request: TurnOnRequest
    ): ResponseEntity<SystemEntity> {
        val localOnTime = LocalTime.parse(request.onTime)
        val localOffTime = LocalTime.parse(request.offTime)

        println("Received onTime: $localOnTime")
        println("Received offTime: $localOffTime")

        val updatedSystem = systemService.turnOnSystem(id, localOnTime, localOffTime)
        return if (updatedSystem != null) {
            ResponseEntity(updatedSystem, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}")
    fun updateSystemTime(
        @PathVariable id: Long,
        @RequestBody request: UpdateTimeRequest
    ): ResponseEntity<SystemEntity> {
        val localOnTime = LocalTime.parse(request.ontime)
        val localOffTime = LocalTime.parse(request.offtime)
        val updatedSystem = systemService.updateSystemTime(id, localOnTime, localOffTime)
        return if (updatedSystem != null) {
            ResponseEntity(updatedSystem, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}/turnOff")
    fun turnOffSystem(@PathVariable id: Long): ResponseEntity<SystemEntity> {
        val updatedSystem = systemService.turnOffSystem(id)
        return if (updatedSystem != null) {
            ResponseEntity(updatedSystem, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    @PutMapping("/{id}/intensity")
    fun updateIntensity(
        @PathVariable id: Long,
        @RequestBody intensityRequest: IntensityRequest
    ): ResponseEntity<SystemEntity> {
        val updatedSystem = systemService.updateIntensity(id, intensityRequest.intensity)
        return if (updatedSystem != null) {
            ResponseEntity(updatedSystem, HttpStatus.OK)
        } else {
            ResponseEntity(HttpStatus.NOT_FOUND)
        }
    }

    data class IntensityRequest(
        val intensity: Int
    )

    data class UpdateTimeRequest(
        val ontime: String,
        val offtime: String
    )

    data class TurnOnRequest(
        val onTime: String,
        val offTime: String
    )
}

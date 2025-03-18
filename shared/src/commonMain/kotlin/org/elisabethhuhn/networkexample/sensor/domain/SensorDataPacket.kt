package org.elisabethhuhn.networkexample.sensor.domain

data class SensorDataPacket(
    val locationTitle: String = "Location, latitude, longitude, altitude, timestamp",
    val locationValue: LocationEvent = LocationEvent(),
    val sensorFrequencyTitle: String = "Read sensors setting = ",
    val sensorFrequency: String,
    val sensorEventTitle: String = "sensor type, rawTime, actualTime from event, time sent to backend, x, y, z",
    val sensorReadings: List<SensorReading> = emptyList()
)

data class LocationEvent (
    val locationValueTitle : String = "Location",
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val altitude: Double = 0.0,
    val timestamp: Long = 0
)

data class SensorReading (
    val sensorType: String,
    val sensorRawTime: Long = 0,
    val sensorActualTime: Long = 0,//from reading the sensor
    val sensorSentTime: Long = 0,//time sent to the backend
    val sensorX: Double = 0.0,
    val sensorY: Double = 0.0,
    val sensorZ: Double = 0.0,
)


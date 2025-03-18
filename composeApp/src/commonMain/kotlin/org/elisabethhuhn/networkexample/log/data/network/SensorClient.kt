package org.elisabethhuhn.networkexample.log.data.network

import io.ktor.client.HttpClient
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType
import io.ktor.util.network.UnresolvedAddressException
import kotlinx.serialization.SerializationException
import org.elisabethhuhn.networkexample.SENSOR_HOST
import org.elisabethhuhn.networkexample.sensor.domain.SensorDataPacket
import org.elisabethhuhn.networkexample.util.NetworkError
import org.elisabethhuhn.networkexample.util.Result


class SensorClient(
    private val httpClient: HttpClient
) {

    suspend fun postSensorReadings(sensorDataPacket: SensorDataPacket): Result<String, NetworkError> {
        val response = try {

            httpClient.post(
                urlString =  SENSOR_HOST
            ) {
                contentType(ContentType.Application.Json)
                setBody(sensorDataPacket)
            }
        } catch(e: UnresolvedAddressException) {
            return Result.Error(NetworkError.NO_INTERNET)
        } catch(e: SerializationException) {
            return Result.Error(NetworkError.SERIALIZATION)
//        } catch (e: ConnectException) {
//            return Result.Error(NetworkError.CONNECTION_FAILED)
        } catch (e: Exception) {
            return Result.Error(NetworkError.UNKNOWN)
        }

        val returnCode = response.status.value
        return when(returnCode) {
            in 200..299 -> {
                Result.Success("Sensor data sent Successfully!!!!!!")
            }
            401 -> Result.Error(NetworkError.UNAUTHORIZED)
            409 -> Result.Error(NetworkError.CONFLICT)
            408 -> Result.Error(NetworkError.REQUEST_TIMEOUT)
            413 -> Result.Error(NetworkError.PAYLOAD_TOO_LARGE)
            429 -> Result.Error(NetworkError.TOO_MANY_REQUESTS)
            in 500..599 -> Result.Error(NetworkError.SERVER_ERROR)
            else -> {
                Result.Error(NetworkError.UNKNOWN)
            }
        }
    }
}
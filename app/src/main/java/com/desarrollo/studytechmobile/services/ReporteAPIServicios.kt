package com.desarrollo.studytechmobile.services
import com.desarrollo.studytechmobile.data.DTOS.ReporteDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class ReporteAPIServicios {
    private val service: IReporteAPIServicios

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.71:7195/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(IReporteAPIServicios::class.java)
    }

    suspend fun obtenerReportes(): List<ReporteDTO> {
        return try {
            service.getReportes()
        } catch (e: IOException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun obtenerReporte(id: Int): ReporteDTO {
        return try {
            service.getReporte(id)
        } catch (e: IOException) {

            ReporteDTO()
        } catch (e: Exception) {
            ReporteDTO()
        }
    }

    suspend fun actualizarReporte(id: Int, reporteDTO: ReporteDTO): Boolean {
        return try {
            service.actualizarReporte(id, reporteDTO)
            true
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    suspend fun crearReporte(reporteDTO: ReporteDTO): Boolean {
        return try {
            service.crearReporte(reporteDTO)
            true
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    suspend fun eliminarReporte(id: Int): Boolean {
        return try {
            service.eliminarReporte(id)
            true
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }
}
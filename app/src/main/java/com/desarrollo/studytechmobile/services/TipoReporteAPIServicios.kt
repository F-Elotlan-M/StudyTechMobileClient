package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.TipoReporteDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class TipoReporteAPIServicios {
    private val service: ITipoReporteAPIServicios

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.71:7195/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ITipoReporteAPIServicios::class.java)
    }

    suspend fun obtenerTiposReporte(): List<TipoReporteDTO> {
        return try {
            service.getTiposReporte()
        } catch (e: IOException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun obtenerTipoReporte(id: Int): TipoReporteDTO {
        return try {
            service.getTipoReporte(id)
        } catch (e: IOException) {
            TipoReporteDTO()
        } catch (e: Exception) {
            TipoReporteDTO()
        }
    }

    suspend fun actualizarTipoReporte(id: Int, tipoReporteDTO: TipoReporteDTO): Boolean {
        return try {
            service.actualizarTipoReporte(id, tipoReporteDTO)
            true
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    suspend fun crearTipoReporte(tipoReporteDTO: TipoReporteDTO): Boolean {
        return try {
            service.crearTipoReporte(tipoReporteDTO)
            true
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    suspend fun eliminarTipoReporte(id: Int): Boolean {
        return try {
            service.eliminarTipoReporte(id)
            true
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }
}
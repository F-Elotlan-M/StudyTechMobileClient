package com.desarrollo.studytechmobile.services
import com.desarrollo.studytechmobile.data.DTOS.ReporteDTO
import com.google.gson.JsonObject
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ReporteAPIServicios {
        private val httpClient: Retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.71:7195/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

    fun crearReporte(reporteDTO: ReporteDTO): Int {
        var Respuesta: Int = 0
        try {
            val service = httpClient.create(IReporteAPIServicios::class.java)
            val jsonBody = JsonObject().apply {
                addProperty("usuarioReporte", reporteDTO.usuarioReporte)
                addProperty("videoReporte", reporteDTO.videoReporte)
                addProperty("tipoReporte", reporteDTO.tipoReporte)
            }

            val call = service.crearReporte(jsonBody)
            val response = call.execute()

            if(response.isSuccessful){
                Respuesta = 1
                println("crear reporte dio: ${response.code()}")
            }else{
                Respuesta = -1
                println("crear reporte dio sino: ${response.code()}")
            }
        }catch (e: Exception){
            println("crear reporte dio excepcion: $e")
            Respuesta = -2
        }
        return Respuesta
    }

    fun obtenerReportes(): List<ReporteDTO>{
        try {
            val service = httpClient.create(IReporteAPIServicios::class.java)
            val call = service.getReportes()
            val response = call.execute()
            val listaReportes: List<ReporteDTO> = if(response.isSuccessful){
                val reportesList = response.body() ?: emptyList()
                reportesList
            }else {
                println("Error al obtener la lista de videos: ${response.code()} - ${response.message()}")
                emptyList()
            }
            return listaReportes
        }catch (e: Exception){
            println("ocurré una excepción: $e")
            return emptyList()
        }
    }

/*
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

    suspend fun crearReporte(reporteDTO: ReporteDTO): Int {
        var Respuesta: Int
        try {
            val service = retrofit
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
    }*/
}
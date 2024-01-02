package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.TipoReporteDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class TipoReporteAPIServicios {


    private val httpClient: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.71:7195/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    suspend fun obtenerTiposReporte(): List<TipoReporteDTO> {
        try{
            val service = httpClient.create(ITipoReporteAPIServicios::class.java)
            val call = service.getTiposReporte()
            val response = call.execute()
            val tipoReporte: List<TipoReporteDTO> = if(response.isSuccessful){
                val listaTipoReporte = response.body() ?: emptyList()
                val respuesta = response.code()
                println("respuesta: $respuesta")
                listaTipoReporte
            }else {
                println("Error al obtener la lista de videos: ${response.code()} - ${response.message()}")
                emptyList()
            }
            return tipoReporte
        }catch(e: Exception){
            println("Excepción al obtener la lista de videos: ${e.message}")
            return emptyList()
        }
    }


    /*
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
*/
}

package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.MateriaDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MateriaAPIServicios {
    private val httpClient: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.71:7195/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun obtenerMaterias(): List<MateriaDTO> {
        try {
            val service = httpClient.create(IMateriaAPIServicios::class.java)
            val call = service.getMaterias()
            val response = call.execute()
            val materia: List<MateriaDTO> = if(response.isSuccessful){
                val listamateria = response.body() ?: emptyList()
                listamateria
            }else{
                println("Error al obtener la lista de videos: ${response.code()} - ${response.message()}")
                emptyList()
            }
            return materia
        }catch (e: Exception){
            println("la excepción es: $e")
            return emptyList()
        }
    }

    /*s
    suspend fun obtenerMateria(id: Int): MateriaDTO {
        return try {
            service.getMateria(id)
        } catch (e: IOException) {
            MateriaDTO() // Objeto vacío u inicializado según tu lógica
        } catch (e: Exception) {
            MateriaDTO() // Objeto vacío u inicializado según tu lógica
        }
    }

    suspend fun actualizarMateria(id: Int, materiaDTO: MateriaDTO): Boolean {
        return try {
            service.actualizarMateria(id, materiaDTO)
            true
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    suspend fun crearMateria(materiaDTO: MateriaDTO): Boolean {
        return try {
            service.crearMateria(materiaDTO)
            true
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    suspend fun eliminarMateria(id: Int): Boolean {
        return try {
            service.eliminarMateria(id)
            true
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }*/
}
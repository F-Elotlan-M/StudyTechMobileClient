package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.DTOS.TipoUsuarioDTO
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class TipoUsuarioAPIServicio {
    private val service: ITipoUsuarioAPIServicio

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl("http://192.168.1.71:7195/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        service = retrofit.create(ITipoUsuarioAPIServicio::class.java)
    }

    suspend fun obtenerTipoUsuarios(): List<TipoUsuarioDTO> {
        return try {
            service.getTipoUsuarios()
        } catch (e: IOException) {
            emptyList()
        } catch (e: Exception) {
            emptyList()
        }
    }

    suspend fun obtenerTipoUsuario(id: Int): TipoUsuarioDTO {
        return try {
            service.getTipoUsuario(id)
        } catch (e: IOException) {
            TipoUsuarioDTO()
        } catch (e: Exception) {
            TipoUsuarioDTO()
        }
    }

    suspend fun actualizarTipoUsuario(id: Int, tipoUsuarioDTO: TipoUsuarioDTO): Boolean {
        return try {
            service.actualizarTipoUsuario(id, tipoUsuarioDTO)
            true
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    suspend fun crearTipoUsuario(tipoUsuarioDTO: TipoUsuarioDTO): Boolean {
        return try {
            service.crearTipoUsuario(tipoUsuarioDTO)
            true
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }

    suspend fun eliminarTipoUsuario(id: Int): Boolean {
        return try {
            service.eliminarTipoUsuario(id)
            true
        } catch (e: IOException) {
            false
        } catch (e: Exception) {
            false
        }
    }
}
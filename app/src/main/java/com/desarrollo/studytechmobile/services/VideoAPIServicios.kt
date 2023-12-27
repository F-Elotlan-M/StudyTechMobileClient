package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.Video
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException

class VideoAPIServicios {
    private val httpClient: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.71:7195/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun obtenerListaVideos(): List<Video> {
        try {
            val service = httpClient.create(IVideoAPIServicios::class.java)
            val call = service.obtenerListaVideos()
            val response = call.execute()

            val video: List<Video> = if (response.isSuccessful){
                val videosList = response.body() ?: emptyList()
                val respuesta = response.code()
                println("Lista de videos: $respuesta")
                videosList
            } else {
                println("Error al obtener la lista de videos: ${response.code()} - ${response.message()}")
                listOf(Video(id = -1, nombre = "Error"))

            }
            return video
        } catch (ex: Exception) {
            println("Excepción al obtener la lista de videos: ${ex.message}")
            return emptyList()
        }
    }

    fun obtenerVideoPorId(id: Int): Video {
        val videoObtenido = Video()
        try {
            val service = httpClient.create(IVideoAPIServicios::class.java)
            val call = service.obtenerVideoPorId(id)
            val response = call.execute()

            return if(response.isSuccessful) {
                response.body() ?: videoObtenido
            } else {
                println("Error al obtener el video con ID $id: ${response.code()} - ${response.message()}")
                videoObtenido.nombre = "Error"
                videoObtenido
            }
        } catch (e: IOException) {
            println("Excepción al obtener el video con ID $id: ${e.message}")
            videoObtenido.nombre = "Excepcion"
            return videoObtenido
        }
    }
}

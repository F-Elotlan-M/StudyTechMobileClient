package com.desarrollo.studytechmobile.services

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class CalificacionVideoAPIServicios {
    private val httpClient: Retrofit = Retrofit.Builder()
        .baseUrl("http://192.168.1.71:7195/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun obtenerCalificacion(idVideo: Int): Int{
        var calificacion: Int
        val service =  httpClient.create(ICalificacionVideoAPIServicios::class.java)
        val call = service.obtenerPromedio(idVideo)
        try {
            val response = call.execute()
            if(response.isSuccessful){
                calificacion = response.body()!!
                println("la respuesta es: $calificacion")
            }else{
                return -1
            }
        }catch (e: Exception){
            println("la excepcion de calificacion: $e")
            calificacion = -2
        }
        println("el return es: $calificacion")
        return calificacion
    }
}
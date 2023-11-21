package com.desarrollo.studytechmobile.services

import com.desarrollo.studytechmobile.data.CredencialesUsuario
import com.desarrollo.studytechmobile.data.RespuestaAutenticacion
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class UsuariosAPIServicios {
    private val httpClient: Retrofit = Retrofit.Builder()
        .baseUrl("https://localhost:7195/api/Cuentas/login")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun login(username: String, password: String): RespuestaAutenticacion {
        try{
            val service = httpClient.create(IUsuariosAPIServicios::class.java)
            var credenciales = RespuestaAutenticacion()
            val datosLogin = CredencialesUsuario()
            datosLogin.username = username
            datosLogin.password = password

            val call = service.login(datosLogin)
            val response = call.execute()
            if(response.isSuccessful){
                val credenciales = response.body()!!
                return credenciales
            }else {
                val credenciales = RespuestaAutenticacion()
                credenciales.token = ""
                return credenciales
            }
        }catch(e: Exception){
            val credenciales = RespuestaAutenticacion()
            credenciales.token = "exception"
            return credenciales
        }
    }
}
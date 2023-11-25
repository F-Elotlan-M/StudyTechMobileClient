package com.desarrollo.studytechmobile.services
import com.desarrollo.studytechmobile.data.CredencialesUsuario
import com.desarrollo.studytechmobile.data.RespuestaAutenticacion
import com.desarrollo.studytechmobile.data.Usuario
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IUsuariosAPIServicios {
    @POST("api/Cuentas/login")
    fun login(@Body credencialesUser: CredencialesUsuario): Call<RespuestaAutenticacion>

    @POST("api/Cuentas/registrar")
    fun registrar(@Body usuario: Usuario): Call<RespuestaAutenticacion>

}
package com.desarrollo.studytechmobile.services
import com.desarrollo.studytechmobile.data.CredencialesUsuario
import com.desarrollo.studytechmobile.data.RespuestaAutenticacion
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

interface IUsuariosAPIServicios {
    @POST("Cuentas/login")
    fun login(@Body credencialesUsuario: CredencialesUsuario): Call<RespuestaAutenticacion>


}
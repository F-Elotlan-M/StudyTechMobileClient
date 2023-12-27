package com.desarrollo.studytechmobile.data

object UsuarioSingleton {
    var Id: Int? = null
    var username:String? = null
    var password:String? = null
    var token:String? = null
    var Tipo: Int? = null
    var prueba: String? = null


    fun borrarSingleton() {
        Id = 0
        username = ""
        password = ""
        token = ""
        Tipo = 0
        prueba = ""
    }
}
@file:Suppress("UnusedImport")

package com.desarrollo.studytechmobile.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.UsuarioSingleton
import com.desarrollo.studytechmobile.services.UsuariosAPIServicios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.invoke
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {
    private val usuariosAPIServicios = UsuariosAPIServicios()
    var usuarioSingleton: UsuarioSingleton = UsuarioSingleton()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin = findViewById<Button>(R.id.login)
        val singinStudent = findViewById<TextView>(R.id.registerStudent)
        val singinAcademic = findViewById<TextView>(R.id.registerAcademic)
        btnLogin.setOnClickListener {
            login()
        }
    }

    private fun login(){
        val username = findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        CoroutineScope(Dispatchers.Main).launch {
            try{
                val userCredentials = withContext(Dispatchers.IO){
                    usuariosAPIServicios.login(username, password)
                }
                if(userCredentials.token == ""){
                    Toast.makeText(applicationContext, "El usuario no ha sido encontrado", Toast.LENGTH_SHORT).show()
                } else if(userCredentials.token == "exception"){
                    Toast.makeText(applicationContext, "No se ha podido conectar con la base de datos", Toast.LENGTH_SHORT).show()
                }else if(userCredentials.token != null){
                    Toast.makeText(applicationContext, "Bienvenido $username", Toast.LENGTH_SHORT).show()
                    usuarioSingleton.token = userCredentials.token;
                }

            }catch (e: Exception) {
                Toast.makeText(applicationContext, "Error de conexión", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
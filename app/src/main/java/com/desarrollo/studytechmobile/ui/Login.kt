package com.desarrollo.studytechmobile.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.UsuarioSingleton
import com.desarrollo.studytechmobile.services.UsuariosAPIServicios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
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
        singinAcademic.setOnClickListener{
            val intent = Intent(this, RegistrarAcademico::class.java)
            startActivity(intent)
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
                var token = userCredentials.token
                if(userCredentials.token == "error"){
                    Toast.makeText(applicationContext, "El usuario no ha sido encontrado", Toast.LENGTH_SHORT).show()
                } else if(userCredentials.token == "exception"){
                    Toast.makeText(applicationContext, "Hubo un problema al realizar la acción, vuelve a intentarlo", Toast.LENGTH_SHORT).show()
                }else if(userCredentials.token != null && userCredentials.token != ""){
                    Toast.makeText(applicationContext, "Bienvenido:  $token", Toast.LENGTH_SHORT).show()
                    usuarioSingleton.token = userCredentials.token;
                    usuarioSingleton.username = username;
                    usuarioSingleton.password = password;
                    val intent = Intent(applicationContext, InicioFragment::class.java)
                    startActivity(intent)
                }

            }catch (e: Exception) {

            }
        }
    }
}
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
import com.desarrollo.studytechmobile.services.TipoReporteAPIServicios
import com.desarrollo.studytechmobile.services.UsuariosAPIServicios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Login : AppCompatActivity() {
    private val usuariosAPIServicios = UsuariosAPIServicios()
    private val tipoReporteAPI = TipoReporteAPIServicios()
    var user: UsuarioSingleton = UsuarioSingleton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val btnLogin = findViewById<Button>(R.id.login)
        val singinStudent = findViewById<TextView>(R.id.registerStudent)
        val singinAcademic = findViewById<TextView>(R.id.registerAcademic)
        btnLogin.setOnClickListener {
            if(login()){
                val intentInicio = Intent(this, InicioFragment::class.java)
                startActivity(intentInicio)
            }
        }
        singinStudent.setOnClickListener{
            val intentStudent = Intent(this, RegistrarEstudiante::class.java)
            startActivity(intentStudent)
        }
        singinAcademic.setOnClickListener{
            val intent = Intent(this, RegistrarAcademico::class.java)
            startActivity(intent)
        }
    }

    private fun login() : Boolean{
        var accept = false
        val username = findViewById<EditText>(R.id.username).text.toString()
        val password = findViewById<EditText>(R.id.password).text.toString()
        if(validateNullFieldsAcademic(username)){
            if(validateNullFieldsAcademic(password)){
                CoroutineScope(Dispatchers.Main).launch {
                    try{
                        val userCredentials = withContext(Dispatchers.IO){
                            usuariosAPIServicios.login(username, password)
                        }
                        var token = userCredentials.token
                        if(userCredentials.token == "error"){
                            accept = false
                            Toast.makeText(applicationContext, "El usuario no ha sido encontrado", Toast.LENGTH_SHORT).show()
                        } else if(userCredentials.token == "exception"){
                            accept = false
                            Toast.makeText(applicationContext, "Hubo un problema al realizar la acción, vuelve a intentarlo", Toast.LENGTH_SHORT).show()
                        }else if(userCredentials.token != null && userCredentials.token != ""){
                            var tipoUsuario = userCredentials.tipoUsuario
                            println("el tipo usuario es: $tipoUsuario")
                            if(userCredentials.tipoUsuario == 2){
                                accept = true
                                Toast.makeText(applicationContext, "Bienvenido:  $username", Toast.LENGTH_SHORT).show()
                                user.Id = userCredentials.id
                                user.token = userCredentials.token;
                                user.username = username;
                                user.password = password;
                                val intent = Intent(applicationContext, BarraActivity::class.java)
                                startActivity(intent)
                            }else{
                                user.Id = userCredentials.id
                                user.token = userCredentials.token;
                                user.username = username;
                                user.password = password;
                                Toast.makeText(applicationContext, "Bienvenido:  $username", Toast.LENGTH_SHORT).show()
                                val intent = Intent(applicationContext, BarrAdminActivity::class.java)
                                startActivity(intent)
                            }
                        }

                    }catch (e: Exception) {
                        println(e)
                    }
                }
            }else{
                Toast.makeText(applicationContext, "Hay campos vacios", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(applicationContext, "Hay campos vacios", Toast.LENGTH_SHORT).show()
        }
        return accept
    }

    fun validateNullFieldsAcademic(cadena: String): Boolean {
        return !cadena.isNullOrBlank() && !cadena.isNullOrBlank() && !cadena.isNullOrBlank()
    }

}
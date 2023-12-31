package com.desarrollo.studytechmobile.ui

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.CredencialesUsuario
import com.desarrollo.studytechmobile.services.UsuariosAPIServicios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class RegistrarEstudiante : AppCompatActivity() {
    private lateinit var user: EditText
    private lateinit var pass: EditText
    private lateinit var name: EditText
    var username: String? = null
    var password: String? = null
    var nombre: String? = null
    var tipo: Int? = null

    private val usuariosAPIServicios = UsuariosAPIServicios()
    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registrar_estudiante)
        val buttonSingin = findViewById<Button>(R.id.botonRegistrarEstudiante)
        val login = findViewById<TextView>(R.id.textLogin)

        buttonSingin.setOnClickListener {
            name = findViewById<EditText>(R.id.nameAcademic)
            user = findViewById<EditText>(R.id.username)
            pass = findViewById<EditText>(R.id.password)
            nombre = name.text.toString()
            username = user.text.toString()
            password = pass.text.toString()
            tipo = 2
            singin()
        }
        login.setOnClickListener{
            val intent = Intent(this, Login::class.java)
            startActivity(intent)
        }
    }
    fun singin(){
        if(validateNullFieldsAcademic()){
            if(validateFieldsAcademic()){
                var credencialesUsuario = CredencialesUsuario()
                credencialesUsuario.nombre = nombre
                credencialesUsuario.password = password
                credencialesUsuario.username = username
                credencialesUsuario.tipoUsuario = 2
                CoroutineScope(Dispatchers.Main).launch {
                    val validateExistingUser = withContext(Dispatchers.IO) {
                        async { usuariosAPIServicios.getExistingUser(username!!) }.await()
                    }
                    if(validateExistingUser!= username){
                        val estadoRegistrarAcademico = withContext(Dispatchers.IO){
                            async{ usuariosAPIServicios.registrarUsuario(credencialesUsuario)}.await()
                        }
                        if(estadoRegistrarAcademico == 1){
                            Toast.makeText(applicationContext, "Estudiante registrado exitosamente", Toast.LENGTH_SHORT).show()
                            name.text?.clear()
                            user.text?.clear()
                            pass.text?.clear()
                        }else if(estadoRegistrarAcademico == 0){
                            Toast.makeText(applicationContext, "El usuario no se ha podido registrar", Toast.LENGTH_SHORT).show()
                        }else if(estadoRegistrarAcademico == 2){
                            Toast.makeText(applicationContext, "Ha ocurrido una excepción", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(applicationContext, "Este nombre de usuario ya existe", Toast.LENGTH_SHORT).show()
                    }
                }
            }else{
                Toast.makeText(applicationContext, "Su nombre es inválido, introduzca su nombre real", Toast.LENGTH_SHORT).show()
            }
        }else{
            Toast.makeText(applicationContext, "Existen campos vacios", Toast.LENGTH_SHORT).show()
        }
    }

    fun validateNullFieldsAcademic(): Boolean {
        return !nombre.isNullOrBlank() && !username.isNullOrBlank() && !password.isNullOrBlank()
    }


    fun validateFieldsAcademic(): Boolean {
        var correctField = false
        correctField = validateSpecialCharacter(nombre!!)
        return correctField
    }

    fun validateSpecialCharacter(verify: String): Boolean {
        var withoutSpecialCharacter = true
        val specialCharacters = "*#+-_;.@%&/()=!?¿¡{}[]^<>"
        for (character in verify) {
            if (character in '0'..'9') {
                withoutSpecialCharacter = false
            }
        }
        for (character in verify) {
            for (specialCharacter in specialCharacters) {
                if (character == specialCharacter) {
                    withoutSpecialCharacter = true
                }
            }
            if (withoutSpecialCharacter) {
                break
            }
        }
        return withoutSpecialCharacter
    }

}
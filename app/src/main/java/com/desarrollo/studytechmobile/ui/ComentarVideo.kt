package com.desarrollo.studytechmobile.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.DTOS.ComentariosDTO
import com.desarrollo.studytechmobile.data.UsuarioSingleton
import com.desarrollo.studytechmobile.services.ComentariosAPIServicios
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class ComentarVideo : AppCompatActivity() {
    private var idVideo: Int = 0
    private lateinit var textInputLayout: TextInputLayout
    private lateinit var textInputEditText: TextInputEditText
    private lateinit var buttonComment: Button
    private val comentariosAPIServicios = ComentariosAPIServicios()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_comentar)

        textInputLayout = findViewById(R.id.textInputLayout)
        textInputEditText = findViewById(R.id.textInputEditText)
        buttonComment = findViewById(R.id.buttonComment)
        idVideo = intent.getIntExtra("idVideo", 0)

        buttonComment.setOnClickListener {
            enviarComentario()
        }
    }

    private fun enviarComentario(){
        var respuesta: Int = 0
        val comentario = textInputEditText.text.toString()
        println("el comentario es: $comentario")
        if(validateNullFieldsAcademic(comentario)){
            var comentarioNuevo = ComentariosDTO()
            comentarioNuevo.comentario = comentario
            comentarioNuevo.videoRelacionado = idVideo
            comentarioNuevo.username = UsuarioSingleton.username

            CoroutineScope(Dispatchers.Main).launch{
                try {
                    respuesta = withContext(Dispatchers.IO) {
                        async { comentariosAPIServicios.crearComentario(comentarioNuevo) }.await()!!
                    }
                    if (respuesta == 1){
                        Toast.makeText(applicationContext, "Comentario registrado", Toast.LENGTH_SHORT).show()
                        textInputEditText.text?.clear()
                    }else if(respuesta == 2){
                        Toast.makeText(applicationContext, "Error al comentar", Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(applicationContext, "Ocurrio una excepción", Toast.LENGTH_SHORT).show()
                    }
                }catch (e: Exception){
                    println("la excepción es: $e")
                }
            }
        }else{
            Toast.makeText(applicationContext, "No puede haber campos vacios", Toast.LENGTH_SHORT).show()
        }
    }

    fun validateNullFieldsAcademic(comentario: String): Boolean {
        return !comentario.isNullOrBlank() && !comentario.isNullOrBlank() && !comentario.isNullOrBlank()
    }

}
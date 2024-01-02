package com.desarrollo.studytechmobile.ui

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.DTOS.ComentariosDTO
import com.desarrollo.studytechmobile.services.ComentariosAPIServicios
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class Comentarios : AppCompatActivity() {
    private lateinit var comentarios: MutableList<ComentariosDTO>
    private val comentariosAPIServicios = ComentariosAPIServicios()
    private lateinit var recyclerView: RecyclerView
    private lateinit var adaptadorComentario: AdaptadorComentario
    private var idVideo: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_comentarios)

        idVideo = intent.getIntExtra("idVideo", 0)

        recyclerView = findViewById(R.id.comentariosRecyclerView)
        recyclerView.setHasFixedSize(true)
        val layoutManager = LinearLayoutManager(this)
        layoutManager.reverseLayout = true
        layoutManager.stackFromEnd = true
        recyclerView.layoutManager = layoutManager

        comentarios = ArrayList()
        recuperarComentariosDelVideo()
    }

    private fun recuperarComentariosDelVideo(){
        CoroutineScope(Dispatchers.Main).launch{
            try{
                if(!isFinishing && !isDestroyed){
                    println("pasó aquí")
                    comentarios = withContext(Dispatchers.IO) {
                        async { comentariosAPIServicios.obtenerListaComentarios(idVideo) }.await()?.toMutableList() ?: mutableListOf()
                    } as MutableList<ComentariosDTO>
                    if(recyclerView != null && !isFinishing && !isDestroyed && comentarios.isNotEmpty()){
                        if(comentarios[0] != null){
                            adaptadorComentario = AdaptadorComentario(this@Comentarios, comentarios)
                            recyclerView.adapter = adaptadorComentario
                        }else{
                            Toast.makeText(this@Comentarios, "Error al obtener los videos", Toast.LENGTH_SHORT).show()
                        }
                    }else{
                        Toast.makeText(this@Comentarios, "Aún no hay comentarios", Toast.LENGTH_SHORT).show()
                    }
                }else{
                    println("algo mal en ")
                }
            }catch (e: Exception){
                println("probando la excepcion: $e")
            }
        }
    }

}

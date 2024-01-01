package com.desarrollo.studytechmobile.ui

import android.os.Bundle
import android.widget.Button
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.Video
import com.desarrollo.studytechmobile.services.VideoAPIServicios
import com.desarrollo.studytechmobile.services.VideoTypeAPIServicios
import com.desarrollo.studytechmobile.utilidades.Mensajes
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch

class VideoReproduccion : AppCompatActivity() {
    private var idVideo: Int = 0
    private var isFavorito: Boolean = false
    private var isMasTarde: Boolean = false
    private lateinit var videosFavoritos: MutableList<Video>
    private lateinit var videosMasTarde: MutableList<Video>
    val videoAPIServicios = VideoAPIServicios()
    val videoType = VideoTypeAPIServicios()


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_reproduccion)
        val videoView = findViewById<VideoView>(R.id.videoView)
        val btnFavoritosVideo = findViewById<Button>(R.id.btnFavoritosVideo)
        val btnTardeVideo = findViewById<Button>(R.id.btnTardeVideo)
        idVideo = intent.getIntExtra("idVideo", 0) //de aquí obtiene el id del vídeo
        isFavorito = intent.getBooleanExtra("isFavorito", false)
        isMasTarde = intent.getBooleanExtra("isMasTarde", false)

        if(isFavorito){
            btnFavoritosVideo.setBackgroundColor(resources.getColor(R.color.botonesPrincipales))
        }
        if(isMasTarde){
            btnTardeVideo.setBackgroundColor(resources.getColor(R.color.botonesPrincipales))
        }

        /*
        * Aquí puede agregar la logica para el video
        * */

        //agrega y elimina de favoritos
        btnFavoritosVideo.setOnClickListener{
            if(isFavorito){
                MainScope().launch(){
                    val respuestaEliminar = videoType.eliminarTypeVideo(idVideo, "Favoritos")
                    if(respuestaEliminar == 204){
                        val mensaje: String = "El video se eliminó de favoritos"
                        val mensajeLlamada = Mensajes()
                        btnFavoritosVideo.setBackgroundColor(resources.getColor(R.color.colorGris))
                        isFavorito = false
                        mensajeLlamada.mostrarMensaje(this@VideoReproduccion, mensaje)
                    }else{
                        println("sucedio un error $respuestaEliminar")
                    }
                }
            }else{
                MainScope().launch {
                    val respuesta = videoType.agregarFavorito(idVideo)
                    if(respuesta != -1){
                        val mensaje: String = "El video se agregó a favoritos"
                        val mensajeLlamada = Mensajes()
                        mensajeLlamada.mostrarMensaje(this@VideoReproduccion, mensaje)
                        btnFavoritosVideo.setBackgroundColor(resources.getColor(R.color.botonesPrincipales))
                        isFavorito = true
                    }else{
                        println("sucedio un error $respuesta")
                    }
                }
            }
        }

        //agrega y elimina de Mas tarde
        btnTardeVideo.setOnClickListener{
            if(isMasTarde){
                MainScope().launch(){
                    val respuestaEliminar = videoType.eliminarTypeVideo(idVideo, "Tarde")
                    if(respuestaEliminar == 204){
                        val mensaje: String = "El video se eliminó de ver más tarde"
                        val mensajeLlamada = Mensajes()
                        btnTardeVideo.setBackgroundColor(resources.getColor(R.color.colorGris))
                        isMasTarde = false
                        mensajeLlamada.mostrarMensaje(this@VideoReproduccion, mensaje)
                    }else{
                        println("sucecio un error $respuestaEliminar")
                    }
                }
            }else{
                MainScope().launch {
                    val respuesta = videoType.agregarMasTarde(idVideo)
                    if(respuesta != -1){
                        val mensaje: String = "El video se agregó a ver más tarde"
                        val mensajeLlamada = Mensajes()
                        mensajeLlamada.mostrarMensaje(this@VideoReproduccion, mensaje)
                        btnTardeVideo.setBackgroundColor(resources.getColor(R.color.botonesPrincipales))
                        isMasTarde = true
                    }
                }
            }
        }


    }

}
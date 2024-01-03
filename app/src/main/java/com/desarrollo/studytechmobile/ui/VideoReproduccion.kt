package com.desarrollo.studytechmobile.ui


import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.Video
import com.desarrollo.studytechmobile.services.VideoAPIServicios
import com.desarrollo.studytechmobile.services.VideoTypeAPIServicios
import com.desarrollo.studytechmobile.utilidades.Mensajes
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.io.IOException

class VideoReproduccion : AppCompatActivity() {


    private var idVideo: Int = 0
    private var isFavorito: Boolean = false
    private var isMasTarde: Boolean = false
    private var calificacion: Int = 0
    private var ruta: String = ""
    private lateinit var videosFavoritos: MutableList<Video>
    private lateinit var videosMasTarde: MutableList<Video>
    val videoAPIServicios = VideoAPIServicios()
    val videoType = VideoTypeAPIServicios()
    private lateinit var playerView: PlayerView
    private lateinit var player: SimpleExoPlayer
    private lateinit var playPauseButton: Button
    private lateinit var rewindButton: Button
    private lateinit var forwardButton: Button
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_reproduccion)
        val btnFavoritosVideo = findViewById<Button>(R.id.btnFavoritosVideo)
        val btnTardeVideo = findViewById<Button>(R.id.btnTardeVideo)
        val btnComentar = findViewById<Button>(R.id.btnComentar)
        val btnVerComentarios = findViewById<Button>(R.id.btnComentarios)
        val btnReportar = findViewById<Button>(R.id.btnReportar)
        val textViewEditable = findViewById<TextView>(R.id.calificacionEditable)
        playerView = findViewById(R.id.playerView)
        playPauseButton = findViewById(R.id.playPauseButton)
        rewindButton = findViewById(R.id.rewindButton)
        forwardButton = findViewById(R.id.fastForwardButton)
        idVideo = intent.getIntExtra("idVideo", 0)
        isFavorito = intent.getBooleanExtra("isFavorito", false)
        isMasTarde = intent.getBooleanExtra("isMasTarde", false)
        calificacion = intent.getIntExtra("calificacion", 0)
        ruta = intent.getStringExtra("ruta").toString()

        textViewEditable.setText(calificacion.toString())

        if(isFavorito){
            btnFavoritosVideo.setBackgroundColor(resources.getColor(R.color.botonesPrincipales))
        }
        if(isMasTarde){
            btnTardeVideo.setBackgroundColor(resources.getColor(R.color.botonesPrincipales))
        }

        btnVerComentarios.setOnClickListener{
            val intent = Intent(this, Comentarios::class.java)
            intent.putExtra("idVideo", idVideo)
            this.startActivity(intent)
        }

        btnComentar.setOnClickListener {
            val intent = Intent(this, ComentarVideo::class.java)
            intent.putExtra("idVideo", idVideo)
            this.startActivity(intent)
        }

        btnReportar.setOnClickListener {
            val intent = Intent(this, Reportar::class.java)
            intent.putExtra("idVideo", idVideo)
            this.startActivity(intent)
        }

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


        try {
            initializePlayer()
        } catch (e: Exception) {
            handlePlayerException(e)
        }

        playPauseButton.setOnClickListener {
            if (isPlaying) {
                player.pause()
                isPlaying = false
                playPauseButton.text = "Play"
            } else {
                player.play()
                isPlaying = true
                playPauseButton.text = "Pause"
            }
        }

        rewindButton.setOnClickListener {
            val currentPosition = player.currentPosition
            if (currentPosition - 10000 > 0) {
                player.seekTo(currentPosition - 10000)
            }
        }

        forwardButton.setOnClickListener {
            val currentPosition = player.currentPosition
            val duration = player.duration
            if (currentPosition + 10000 < duration) {
                player.seekTo(currentPosition + 10000)
            }
        }
    }

    private fun initializePlayer() {
        player = SimpleExoPlayer.Builder(this, DefaultRenderersFactory(this)).build()
        playerView.player = player

        if(ruta.length < 20){
            ruta = "https://studyandroid.s3.us-east-2.amazonaws.com/1+minuto+-+%E2%8C%9A+Temporizador+y+Alarma+%E2%8F%B0+Cuenta+Regresiva.mp4"
        }


        val mediaItem = MediaItem.fromUri(ruta)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        player.addListener(object : Player.Listener {
            fun onPlayerError(error: ExoPlaybackException) {
                handlePlayerException(error)
            }
        })
    }

    private fun handlePlayerException(exception: Exception) {
        when (exception) {
            is IOException -> {
                Toast.makeText(this, "Error de lectura de archivo", Toast.LENGTH_SHORT).show()
            }
            is IllegalStateException -> {
                Toast.makeText(this, "Estado inválido del reproductor", Toast.LENGTH_SHORT).show()
            }
            is ExoPlaybackException -> {
                val errorString = exception.message ?: "Error de reproducción desconocido"
                Toast.makeText(this, "Error de reproducción: $errorString", Toast.LENGTH_SHORT)
                    .show()
            }
            else -> {
                Toast.makeText(this, "Error desconocido", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        player.release()
    }
}
package com.desarrollo.studytechmobile.ui

import android.os.Bundle
import android.net.Uri
import android.widget.Button
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.R.id.playerView
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.SimpleExoPlayer


class VideoReproduccion : AppCompatActivity() {

    private lateinit var playerView: PlayerView
    private  lateinit var player: SimpleExoPlayer
    private lateinit var playPauseButton: Button
    private lateinit var rewindButton : Button
    private lateinit var forwardButton : Button
    private var isPlaying = false


    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_reproduccion)

        playerView = findViewById(R.id.playerView)
        playPauseButton = findViewById(R.id.playPauseButton)
        rewindButton = findViewById(R.id.rewindButton)
        forwardButton = findViewById(R.id.fastForwardButton)


        player = SimpleExoPlayer.Builder(this, DefaultRenderersFactory(this)).build()
        playerView.player = player

        val videoUrl = "https://studyandroid.s3.us-east-2.amazonaws.com/1+minuto+-+%E2%8C%9A+Temporizador+y+Alarma+%E2%8F%B0+Cuenta+Regresiva.mp4"

        val mediaItem = MediaItem.fromUri(videoUrl)
        player.setMediaItem(mediaItem)
        player.prepare()
        player.play()

        playPauseButton.setOnClickListener{
            if(isPlaying){
                player.pause()
                isPlaying = false
                playPauseButton.text = "Play"
            }else{
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

        forwardButton.setOnClickListener{
            val currentPosition = player.currentPosition
            if(currentPosition + 10000  < player.duration) {
                player.seekTo(currentPosition + 10000)
            }
        }

        //pendiente agregar la lógica a estos botones
        btnFavoritosVideo.setOnClickListener{
            Toast.makeText(applicationContext, "Se agrega a favoritos", Toast.LENGTH_SHORT).show()
        }
        btnTardeVideo.setOnClickListener{
            Toast.makeText(applicationContext, "Se agrega a ver más tarde", Toast.LENGTH_SHORT).show()
        }


    }


}
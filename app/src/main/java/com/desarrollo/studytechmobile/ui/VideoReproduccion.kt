package com.desarrollo.studytechmobile.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.desarrollo.studytechmobile.R
import com.google.android.exoplayer2.DefaultRenderersFactory
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.ui.PlayerView
import java.io.IOException

class VideoReproduccion : AppCompatActivity() {

    private lateinit var playerView: PlayerView
    private lateinit var player: SimpleExoPlayer
    private lateinit var playPauseButton: Button
    private lateinit var rewindButton: Button
    private lateinit var forwardButton: Button
    private var isPlaying = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_reproduccion)

        playerView = findViewById(R.id.playerView)
        playPauseButton = findViewById(R.id.playPauseButton)
        rewindButton = findViewById(R.id.rewindButton)
        forwardButton = findViewById(R.id.fastForwardButton)

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

        val videoUrl =
            "https://studyandroid.s3.us-east-2.amazonaws.com/1+minuto+-+%E2%8C%9A+Temporizador+y+Alarma+%E2%8F%B0+Cuenta+Regresiva.mp4"

        val mediaItem = MediaItem.fromUri(videoUrl)
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

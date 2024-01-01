package com.desarrollo.studytechmobile.ui

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import android.widget.VideoView
import androidx.appcompat.app.AppCompatActivity
import com.desarrollo.studytechmobile.R

class VideoReproduccion : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)
        setContentView(R.layout.video_reproduccion)
        val videoView = findViewById<VideoView>(R.id.videoView)
        val btnFavoritosVideo = findViewById<Button>(R.id.btnFavoritosVideo)
        val btnTardeVideo = findViewById<Button>(R.id.btnTardeVideo)

        /*
        * Aquí puede agregar la logica para el video
        * */


        //pendiente agregar la lógica a estos botones
        btnFavoritosVideo.setOnClickListener{
            Toast.makeText(applicationContext, "Se agrega a favoritos", Toast.LENGTH_SHORT).show()
        }
        btnTardeVideo.setOnClickListener{
            Toast.makeText(applicationContext, "Se agrega a ver más tarde", Toast.LENGTH_SHORT).show()
        }


    }
}
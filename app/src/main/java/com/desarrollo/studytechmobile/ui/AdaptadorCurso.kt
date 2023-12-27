package com.desarrollo.studytechmobile.ui

import android.content.Context
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.Video
import com.desarrollo.studytechmobile.utilidades.FormatoFechas

// ...

class AdaptadorCurso(private val context: Context, private val Videos: List<Video>) :
    RecyclerView.Adapter<AdaptadorCurso.CourseViewHolder>() {

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseImage: ImageView = itemView.findViewById(R.id.courseImage)
        val courseTitle: TextView = itemView.findViewById(R.id.courseTitle)
        val courseDate: TextView = itemView.findViewById(R.id.courseDate)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_videocard, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val currentVideo = Videos[position]

        if (currentVideo.imagen?.length ?: 0 >= 100) {
            val toRemove = "data:image/jpeg;base64,"
            currentVideo.imagen = currentVideo.imagen.removePrefix(toRemove)
            Glide.with(context)
                .load(decodeBase64(currentVideo.imagen))
                .into(holder.courseImage)
        } else {
            Glide.with(context)
                .load(R.drawable.noimage)
                .into(holder.courseImage)
        }

        holder.courseTitle.text = currentVideo.nombre
        val llamada = FormatoFechas()
        val fechaSubida = llamada.formatoFechasString(currentVideo.fechaSubida)
        holder.courseDate.text = fechaSubida
        holder.itemView.setOnClickListener {
            val nombreVideo = currentVideo.id
        }
    }

    override fun getItemCount(): Int {
        return Videos.size
    }

    // Función para decodificar el String Base64
    private fun decodeBase64(base64String: String?): ByteArray {
        try {
            return Base64.decode(base64String, Base64.DEFAULT)
        } catch (e: Exception) {
            e.printStackTrace()
            // Manejo de errores: Puedes mostrar un mensaje o registrar información en el log
            return ByteArray(0)
        }
    }
}

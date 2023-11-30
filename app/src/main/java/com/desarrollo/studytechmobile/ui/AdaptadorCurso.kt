package com.desarrollo.studytechmobile.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.Video

class AdaptadorCurso (private val context: Context, private val Videos: List<Video>) :
RecyclerView.Adapter<AdaptadorCurso.CourseViewHolder>() {

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val courseImage: ImageView = itemView.findViewById(R.id.courseImage)
        val courseTitle: TextView = itemView.findViewById(R.id.courseTitle)
        val courseDescription: TextView = itemView.findViewById(R.id.courseDescription)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_videocard, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val currentVideos = Videos[position]

        // Asignar datos a la tarjeta
        holder.courseTitle.text = currentVideos.Nombre
        holder.courseDescription.text = currentVideos.FechaModificacion
        // Aquí deberías cargar la imagen utilizando una biblioteca como Glide o Picasso
        // Ejemplo: Glide.with(context).load(currentCourse.imageUrl).into(holder.courseImage)

        // Manejar clics en las tarjetas si es necesario
        holder.itemView.setOnClickListener {
            // Lógica para manejar el clic en la tarjeta
        }
    }

    override fun getItemCount(): Int {
        return Videos.size
    }
}
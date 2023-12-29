package com.desarrollo.studytechmobile.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.DTOS.ComentariosDTO

class AdaptadorComentario(private val context: Context, private val comentarios: List<ComentariosDTO>) :
    RecyclerView.Adapter<AdaptadorComentario.ComentarioViewHolder>() {

    inner class ComentarioViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val comentarioTextView: TextView = itemView.findViewById(R.id.comentario)
        val comentarioUsuario : TextView = itemView.findViewById(R.id.comentarioNombre)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ComentarioViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_comentario, parent, false)
        return ComentarioViewHolder(view)
    }

    override fun onBindViewHolder(holder: ComentarioViewHolder, position: Int) {
        val comentario = comentarios[position]
        holder.comentarioTextView.text = comentario.comentario
        holder.comentarioUsuario.text = comentario.username
    }

    override fun getItemCount(): Int {
        return comentarios.size
    }


}
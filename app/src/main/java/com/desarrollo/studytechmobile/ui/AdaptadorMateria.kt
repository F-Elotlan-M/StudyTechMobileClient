package com.desarrollo.studytechmobile.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.DTOS.MateriaDTO
import com.desarrollo.studytechmobile.utilidades.Mensajes

class AdaptadorMateria (private val context: Context, private var materias: MutableList<MateriaDTO>):
    RecyclerView.Adapter<AdaptadorMateria.MaterieViewHolder>() {

    inner class MaterieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val materiaEdit: TextView = itemView.findViewById(R.id.nombreMateriaEditable)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MaterieViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_materia, parent, false)
        return MaterieViewHolder(view)
    }

    override fun onBindViewHolder(holder: MaterieViewHolder, position: Int) {
        val materia = materias[position]
        holder.materiaEdit.text = materia.nombre

        holder.itemView.setOnClickListener{
            val mensaje: String = "Para manejar los cursos utilice el sistema de escritorio"
            val mensajeLlamada = Mensajes()
            mensajeLlamada.mostrarMensaje(context, mensaje)
        }
    }

    override fun getItemCount(): Int {
        return materias.size
    }
}
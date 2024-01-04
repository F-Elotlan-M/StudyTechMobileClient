package com.desarrollo.studytechmobile.ui

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.ReporteVisual
import com.desarrollo.studytechmobile.utilidades.Mensajes

class AdaptadorReporte (private val context: Context, private var reportes: MutableList<ReporteVisual>) :
RecyclerView.Adapter<AdaptadorReporte.ReporteViewHolder>() {

    inner class ReporteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val videoReportado: TextView = itemView.findViewById(R.id.videoNombreReporte)
        val tipoReporte: TextView = itemView.findViewById(R.id.tipoReporteCard)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReporteViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_reporte, parent, false)
        return ReporteViewHolder(view)
    }

    override fun onBindViewHolder(holder: ReporteViewHolder, position: Int) {
        val reporte = reportes[position]
        holder.videoReportado.text = reporte.videoNombre
        holder.tipoReporte.text = reporte.tipoReporteNombre

        holder.itemView.setOnClickListener{
            val mensaje: String = "Para atender el reporte utilice el sistema de escritorio"
            val mensajeLlamada = Mensajes()
            mensajeLlamada.mostrarMensaje(context, mensaje)
        }
    }

    override fun getItemCount(): Int {
        return reportes.size
    }

}
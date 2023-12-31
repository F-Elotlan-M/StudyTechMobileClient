package com.desarrollo.studytechmobile.ui

import android.content.Context
import android.content.Intent
import android.util.Base64
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Filter
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.desarrollo.studytechmobile.R
import com.desarrollo.studytechmobile.data.DTOS.CalificacionDTO
import com.desarrollo.studytechmobile.data.UsuarioSingleton
import com.desarrollo.studytechmobile.data.Video
import com.desarrollo.studytechmobile.services.CalificacionVideoAPIServicios
import com.desarrollo.studytechmobile.services.VideoTypeAPIServicios
import com.desarrollo.studytechmobile.utilidades.FormatoFechas
import com.desarrollo.studytechmobile.utilidades.Mensajes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Locale

// ...

class AdaptadorCurso(private val context: Context, private var Videos: MutableList<Video>) :
    RecyclerView.Adapter<AdaptadorCurso.CourseViewHolder>() {

    inner class CourseViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val courseImage: ImageView = itemView.findViewById(R.id.courseImage)
        val courseTitle: TextView = itemView.findViewById(R.id.courseTitle)
        val courseDate: TextView = itemView.findViewById(R.id.courseDate)
        val btnFavoritos: Button = itemView.findViewById(R.id.btnAgregarFavoritos)
        val btnMasTarde: Button = itemView.findViewById(R.id.btnAgregarVerMasTarde)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CourseViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.row_videocard, parent, false)
        return CourseViewHolder(view)
    }

    override fun onBindViewHolder(holder: CourseViewHolder, position: Int) {
        val currentVideo = Videos[position]
        val videoType =  VideoTypeAPIServicios()
        val calificacion = CalificacionVideoAPIServicios()

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
        val colorBotonesSeleccionados = ContextCompat.getColor(context, R.color.botonesPrincipales)
        val colorBotonesSinSeleccion = ContextCompat.getColor(context, R.color.colorGris)
        holder.courseDate.text = fechaSubida

        //se comprueba si es favorito o no
        if(currentVideo.isFavorito){
            holder.btnFavoritos.setBackgroundColor(colorBotonesSeleccionados)
        }

        //se comprueba si es para ver más tarde
        if(currentVideo.isMasTarde){
            holder.btnMasTarde.setBackgroundColor(colorBotonesSeleccionados)
        }


        //escucha al botón de favoritos
        holder.btnFavoritos.setOnClickListener {
            if(currentVideo.isFavorito){
                MainScope().launch(){
                    val respuestaEliminar = videoType.eliminarTypeVideo(currentVideo.id, "Favoritos")
                    if(respuestaEliminar == 204){
                        val mensaje: String = "El video se eliminó de favoritos"
                        val mensajeLlamada = Mensajes()
                        holder.btnFavoritos.setBackgroundColor(colorBotonesSinSeleccion)
                        currentVideo.isFavorito = false
                        mensajeLlamada.mostrarMensaje(context, mensaje)
                    }else{
                        println("sucecio un error $respuestaEliminar")
                    }
                }
            }else{
                MainScope().launch {
                    val respuesta = videoType.agregarFavorito(currentVideo.id)
                    if(respuesta != -1){
                        val mensaje: String = "El video se agregó a favoritos"
                        val mensajeLlamada = Mensajes()
                        mensajeLlamada.mostrarMensaje(context, mensaje)
                        holder.btnFavoritos.setBackgroundColor(colorBotonesSeleccionados)
                        currentVideo.isFavorito = true
                    }
                }
            }
        }

        holder.btnMasTarde.setOnClickListener {
            if(currentVideo.isMasTarde){
                MainScope().launch {
                    val respuesta = videoType.eliminarTypeVideo(currentVideo.id, "Tarde")
                    if(respuesta == 204){
                        val mensaje = "El video se eliminó de Más tarde"
                        val mensajeLlamada = Mensajes()
                        holder.btnMasTarde.setBackgroundColor(colorBotonesSinSeleccion)
                        currentVideo.isMasTarde = false
                        mensajeLlamada.mostrarMensaje(context, mensaje)
                    }
                }
                holder.btnMasTarde.setBackgroundColor(colorBotonesSinSeleccion)
                currentVideo.isMasTarde = false
            }else{
                MainScope().launch {
                    val respuesta = videoType.agregarMasTarde(currentVideo.id)
                    if(respuesta != -1){
                        val mensaje: String = "El video se agregó a más tarde"
                        val mensajeLlamada = Mensajes()
                        mensajeLlamada.mostrarMensaje(context, mensaje)
                        holder.btnMasTarde.setBackgroundColor(colorBotonesSeleccionados)
                        currentVideo.isMasTarde = true
                    }
                }

            }
        }

        holder.itemView.setOnClickListener {
            var calificacionVideo: Int = 0
            var calificacionObjeto: CalificacionDTO? = null
            var idUsuario: Int? = UsuarioSingleton.Id
            MainScope().launch {
                calificacionVideo = withContext(Dispatchers.IO){
                    async { calificacion.obtenerCalificacion(currentVideo.id) }.await()!!
                }

                calificacionObjeto = withContext(Dispatchers.IO) {
                    async { calificacion.buscarCalificacion(currentVideo.id, idUsuario) }.await()!!
                }


                if(calificacionVideo < 0){
                    calificacionVideo = 0
                }
                val url = currentVideo.ruta
                val idVideo = currentVideo.id
                val isFavorito = currentVideo.isFavorito
                val isMasTarde = currentVideo.isMasTarde
                val idCalificacion = calificacionObjeto!!.id
                val calificacionUsuario = calificacionObjeto!!.calificacionUsuario
                println("aquiiiii: $idCalificacion y esto $calificacionUsuario")
                val intent = Intent(context, VideoReproduccion::class.java)
                intent.putExtra("idVideo", idVideo)
                intent.putExtra("isFavorito", isFavorito)
                intent.putExtra("isMasTarde", isMasTarde)
                intent.putExtra("calificacion", calificacionVideo)
                intent.putExtra("ruta", url)
                intent.putExtra("idCalificacion", idCalificacion)
                intent.putExtra("calificacionUsuario", calificacionUsuario)
                context.startActivity(intent)
            }

            /*
            */
        }
            /*
            val mensaje: String = "El video es $nombreVideo"
            val mensajeLlamada = Mensajes()
            mensajeLlamada.mostrarMensaje(context, mensaje)
        */


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

    fun updateData(newList: List<Video>) {
        Videos.clear()
        Videos.addAll(newList)
        notifyDataSetChanged()
    }

    fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val filteredList = mutableListOf<Video>()
                val query = constraint?.toString()?.toLowerCase(Locale.getDefault()) ?: ""

                for (video in Videos) {
                    if (video.nombre.toLowerCase(Locale.getDefault()).contains(query)) {
                        filteredList.add(video)
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                val filteredList = results?.values as? MutableList<Video> ?: mutableListOf()
                Videos = filteredList
                notifyDataSetChanged()
            }
        }
    }
}

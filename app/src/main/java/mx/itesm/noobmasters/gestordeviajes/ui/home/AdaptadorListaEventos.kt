package mx.itesm.noobmasters.gestordeviajes.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import mx.itesm.noobmasters.gestordeviajes.R
import mx.itesm.noobmasters.gestordeviajes.model.Evento

class AdaptadorListaEventos(var arrEventos:ArrayList<Evento>):
RecyclerView.Adapter<AdaptadorListaEventos.EventoViewHolder>()
{


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {

        val vistaReglon = LayoutInflater.from(parent.context)
            .inflate(R.layout.renglon_evento, parent, false)
        return EventoViewHolder(vistaReglon)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        holder.set(arrEventos[position])
    }


    override fun getItemCount(): Int {
        return arrEventos.size
    }

    fun actualizarDatos(lista: List<Evento>?){
        arrEventos.clear()

        if(lista!= null){
        arrEventos.addAll(lista)
        }
        notifyDataSetChanged()
    }

    class EventoViewHolder(vista: View):RecyclerView.ViewHolder(vista)
    {


        private val tvNombreEvento = vista.findViewById<TextView>(R.id.tvNombreActividad)


        private val tvFecha = vista.findViewById<TextView>(R.id.tvFecha)


        fun set(evento:Evento) {

            tvNombreEvento.text=evento.nombre

            if(evento.fecha.isNullOrEmpty()){
                tvFecha.text="Sin fecha asignada"
            }else{
                tvFecha.text=evento.fecha
            }
        }

    }
}
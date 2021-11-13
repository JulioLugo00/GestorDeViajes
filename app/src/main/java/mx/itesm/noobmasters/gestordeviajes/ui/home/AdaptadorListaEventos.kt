package mx.itesm.noobmasters.gestordeviajes.ui.home

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import mx.itesm.noobmasters.gestordeviajes.R
import mx.itesm.noobmasters.gestordeviajes.model.Evento

class AdaptadorListaEventos(var arrEventos:ArrayList<Evento>):
RecyclerView.Adapter<AdaptadorListaEventos.EventoViewHolder>()
{
    var listener: RenglonListener?=null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventoViewHolder {

        val vistaReglon = LayoutInflater.from(parent.context)
            .inflate(R.layout.renglon_evento, parent, false)
        return EventoViewHolder(vistaReglon)
    }

    override fun onBindViewHolder(holder: EventoViewHolder, position: Int) {
        holder.set(arrEventos[position])
        val renglon=holder.itemView.findViewById<FrameLayout>(R.id.layoutRenglon)
        renglon.setOnClickListener {

            listener?.clickEnRenglon(position)

        }

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
        private val vistaParametro=vista

        private val tvNombreEvento = vista.findViewById<TextView>(R.id.tvNombreActividad)


        private val tvFechaInicio = vista.findViewById<TextView>(R.id.tvFechaInicio)

        private val tvFechaFin = vista.findViewById<TextView>(R.id.tvFechaFin)

        private  val imagenFondo=vista.findViewById<ImageView>(R.id.imgFondo)

        fun set(evento:Evento) {

            tvNombreEvento.text=evento.nombre

            if(!evento.imagen.isNullOrEmpty()){
                Glide.with(vistaParametro).load(evento.imagen).into(imagenFondo);
            }else{
                imagenFondo.setImageResource(R.drawable.fondo_ejemplo03)
            }

            if(evento.fechaInicio.isNullOrEmpty()){
                tvFechaInicio.text="Sin fecha asignada"
            }else{
                tvFechaInicio.text="Del ${evento.fechaInicio}"
            }

            if(evento.fechaFin.isNullOrEmpty()){
                tvFechaFin.text="Sin fecha asignada"
            }else{
                tvFechaFin.text="al: ${evento.fechaFin}"
            }

        }

    }
}
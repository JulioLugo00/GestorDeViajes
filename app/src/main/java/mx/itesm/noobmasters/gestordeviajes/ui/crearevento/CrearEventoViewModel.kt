package mx.itesm.noobmasters.gestordeviajes.ui.crearevento

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import mx.itesm.noobmasters.gestordeviajes.model.Evento
import mx.itesm.noobmasters.gestordeviajes.model.EventoTipo

class CrearEventoViewModel : ViewModel() {
    //val presupuestoMLD = MutableLiveData<Int>()
    //val nombreMLD = MutableLiveData<String>()
    //val fechaMLD = MutableLiveData<String>()
    //val tipoMLD = MutableLiveData<EventoTipo>()
    //val ubicacionMLD = MutableLiveData<String>()
    //val imagenMLD = MutableLiveData<String>()

    fun crearEvento(presupuesto: Int, nombre: String, fechaInicio: String,fechaFin: String, tipo: EventoTipo, ubicacion: String,
        imagen:String,id:String): Evento{
        val evento = Evento(nombre, tipo, presupuesto, ubicacion, imagen, fechaInicio,fechaFin,id)
        return evento
    }
}
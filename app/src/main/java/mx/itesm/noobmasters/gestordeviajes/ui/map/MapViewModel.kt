package mx.itesm.noobmasters.gestordeviajes.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MapViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Bienvenido, ¿Ya sabes a dónde iras?"
    }
    val text: LiveData<String> = _text
}
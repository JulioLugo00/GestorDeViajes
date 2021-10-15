package mx.itesm.noobmasters.gestordeviajes.ui.actividades

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ActividadesViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
        value = "Fragment de actividades"
    }
    val text: LiveData<String> = _text
}
package mx.itesm.noobmasters.gestordeviajes.ui.eventoInfo

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import mx.itesm.noobmasters.gestordeviajes.R
import mx.itesm.noobmasters.gestordeviajes.databinding.EventoInfoFragmentBinding

class EventoInfoFrag : Fragment() {

    companion object {
        fun newInstance() = EventoInfoFrag()
    }

    private lateinit var viewModel: EventoInfoVM

    private val args:EventoInfoFragArgs by navArgs()

    private lateinit var binding:EventoInfoFragmentBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        //return inflater.inflate(R.layout.evento_info_fragment, container, false)
        binding= EventoInfoFragmentBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(EventoInfoVM::class.java)
        // TODO: Use the ViewModel

        binding.tvNombreEvento.setText("nombre ${args.eventoInfo.nombre}")
        binding.tvCodigoEvento.setText("codigo invitacion ${args.eventoInfo.idUnico}")
    }

}
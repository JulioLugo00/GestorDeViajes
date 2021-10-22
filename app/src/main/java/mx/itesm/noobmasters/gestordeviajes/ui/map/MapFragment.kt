package mx.itesm.noobmasters.gestordeviajes.ui.map

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import mx.itesm.noobmasters.gestordeviajes.MapActivity
import mx.itesm.noobmasters.gestordeviajes.databinding.FragmentMapaBinding

class MapFragment : Fragment() {

    private lateinit var mapViewModel: MapViewModel

    //private var _binding: FragmentDashboardBinding? = null

    private lateinit var binding:FragmentMapaBinding

    // This property is only valid between onCreateView and
    // onDestroyView.
    //private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapViewModel =
            ViewModelProvider(this).get(MapViewModel::class.java)

        //_binding = FragmentDashboardBinding.inflate(inflater, container, false)
        binding= FragmentMapaBinding.inflate(layoutInflater)

        //val root: View = binding.root

        val textView: TextView = binding.textDashboard


        mapViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })

        binding.btnMap.setOnClickListener {
            val intencion = Intent(context, MapActivity::class.java)
            startActivity(intencion)
        }

        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        //_binding = null
    }
}
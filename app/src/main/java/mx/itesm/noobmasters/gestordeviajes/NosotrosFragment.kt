package mx.itesm.noobmasters.gestordeviajes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import mx.itesm.noobmasters.gestordeviajes.databinding.FragmentHomeBinding
import mx.itesm.noobmasters.gestordeviajes.databinding.FragmentNosotrosBinding
import mx.itesm.noobmasters.gestordeviajes.ui.map.MapViewModel

class NosotrosFragment: Fragment() {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var binding: FragmentNosotrosBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding= FragmentNosotrosBinding.inflate(layoutInflater);

        return binding.root

    }
}
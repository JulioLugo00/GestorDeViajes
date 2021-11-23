package mx.itesm.noobmasters.gestordeviajes

import android.content.Intent
import android.os.Bundle
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.database.FirebaseDatabase
import mx.itesm.noobmasters.gestordeviajes.databinding.ActivityMain2Binding
import mx.itesm.noobmasters.gestordeviajes.model.Evento
import mx.itesm.noobmasters.gestordeviajes.model.EventoTipo

class MainActivity2 : AppCompatActivity() {

    private lateinit var binding: ActivityMain2Binding

    //Eliminar
    private lateinit var baseDatos: FirebaseDatabase

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMain2Binding.inflate(layoutInflater)
        setContentView(binding.root)

        val navView: BottomNavigationView = binding.navView

        val navController = findNavController(R.id.nav_host_fragment_activity_main3)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_map
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        //Esconde el titulo de la app
        supportActionBar?.hide()

        binding.btnUsusario.setOnClickListener{
            val intUsuario = Intent(this, UsuarioActivity::class.java)
            startActivity(intUsuario)
        }
    //inicializarBase()
    }

    private fun inicializarBase() {
        baseDatos = FirebaseDatabase.getInstance()

        val evento1=Evento("camping",EventoTipo.SALIDAS,2000,"Estado de Mexico","https://www.dzoom.org.es/wp-content/uploads/2019/09/paisajes-expresivos-734x489.jpg","20 de octubre del 2021","27 de octubre del 2021")

        val evento2=Evento("Gran premio de México F1",EventoTipo.SALIDAS,50000,"Iztacalco CDMX","https://www.dzoom.org.es/wp-content/uploads/2019/09/paisajes-expresivos-734x489.jpg","05 de noviembre de 2021","07 de noviembre de 2021")
        val evento3=Evento("Cita",EventoTipo.CITAS,5000,"Polanco, CDMX","https://www.dzoom.org.es/wp-content/uploads/2019/09/paisajes-expresivos-734x489.jpg")

        val evento4=Evento("Ejemplo",EventoTipo.CITAS,5000,"Polanco, CDMX","https://www.dzoom.org.es/wp-content/uploads/2019/09/paisajes-expresivos-734x489.jpg")

        val ref1=baseDatos.getReference("eventos/1")
        ref1.setValue(evento1)

        val ref2=baseDatos.getReference("eventos/2")
        ref2.setValue(evento2)

        val ref3=baseDatos.getReference("eventos/3")
        ref3.setValue(evento3)

        val ref4=baseDatos.getReference("eventos/4")
        ref4.setValue(evento4)
    }
}
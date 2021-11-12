package mx.itesm.noobmasters.gestordeviajes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.bumptech.glide.Glide
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import mx.itesm.noobmasters.gestordeviajes.databinding.ActivityMain2Binding
import mx.itesm.noobmasters.gestordeviajes.databinding.ActivityUsuarioBinding

class UsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsuarioBinding
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        binding = ActivityUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        configurarEventos()
        configurarTextos()
    }


    private fun configurarTextos() {
        binding.textView3.text = mAuth.currentUser!!.displayName.toString()

        var imageView = binding.imageView5
        Glide.with(this).load(mAuth.currentUser!!.photoUrl).into(imageView)
    }

    private fun configurarEventos() {
        binding.btnRegresar.setOnClickListener{
            val intMain2 = Intent(this, MainActivity2::class.java)
            startActivity(intMain2)
        }

        binding.btnSignOut.setOnClickListener{
            binding.pgUsuario.visibility=View.VISIBLE
            AuthUI.getInstance().signOut(this)
            cambiarPantallaConDelay()
            //binding.pgUsuario.visibility=View.INVISIBLE
        }
    }

    private fun cambiarPantallaConDelay() {
        Handler(Looper.getMainLooper()).postDelayed({

            val intLogIn = Intent(this, LogInActivity::class.java)
            startActivity(intLogIn)


        },1000)
    }


}
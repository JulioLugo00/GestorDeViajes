package mx.itesm.noobmasters.gestordeviajes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.view.isVisible
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.firebase.ui.auth.AuthUI
import com.firebase.ui.auth.IdpResponse
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import mx.itesm.noobmasters.gestordeviajes.databinding.ActivityMain2Binding
import mx.itesm.noobmasters.gestordeviajes.databinding.ActivityUsuarioBinding

class UsuarioActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUsuarioBinding

    private val CODIGO_SIGNIN = 100
    private val mAuth = FirebaseAuth.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_usuario)

        binding = ActivityUsuarioBinding.inflate(layoutInflater)
        setContentView(binding.root)

        supportActionBar?.hide()
        configurarEventos()
    }

    override fun onStart(){
        super.onStart()
        val usuario = mAuth.currentUser
        if (usuario != null) {
            println("INICIA: ${usuario?.displayName}")
            println("Correo: ${usuario?.email}")
            println("UID: ${usuario?.uid}")
            mostrarDatos()
        } else {
            ocultarDatos()
            println("Hacer SignIn")
        }
    }

    private fun ocultarDatos() {
        binding.linearLayout3.isVisible = false
        binding.imageView5.isVisible = false
        binding.imageButton2.isVisible = false
        binding.btnSignOut.isVisible = false
        binding.btnSignIn.isVisible = true
    }
    private fun mostrarDatos() {
        binding.linearLayout3.isVisible = true
        binding.imageView5.isVisible = true
        binding.imageButton2.isVisible = true
        binding.btnSignOut.isVisible = true
        binding.btnSignIn.isVisible = false
    }

    private fun autenticar() {
        val providers =
            arrayListOf(AuthUI.IdpConfig.GoogleBuilder().build())
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setAvailableProviders(providers)
                .build(),
            CODIGO_SIGNIN
        )
        mostrarDatos()
    }

    private fun configurarEventos() {
        binding.btnRegresar.setOnClickListener{
            val intMain2 = Intent(this, MainActivity2::class.java)
            startActivity(intMain2)
        }

        binding.btnSignIn.setOnClickListener{
            autenticar()
        }
        binding.btnSignOut.setOnClickListener{
            AuthUI.getInstance().signOut(this)
            ocultarDatos()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == CODIGO_SIGNIN) {
            when (resultCode) {
                RESULT_OK -> {
                    val usuario = FirebaseAuth.getInstance().currentUser
                    println("Bienvenido: ${usuario?.displayName}")
                    println("Correo: ${usuario?.email}")
                    println("Correo: ${usuario?.uid}")
                    // Lanzar otra actividad
                }
                RESULT_CANCELED -> {
                    println("Cancelado...")
                    val response = IdpResponse.fromResultIntent(data)
                    println("Error:${response?.error?.localizedMessage}")
                }
                else -> {
                    val response = IdpResponse.fromResultIntent(data)
                    println("Error:${response?.error?.errorCode}")
                }
            }
        }
    }

}
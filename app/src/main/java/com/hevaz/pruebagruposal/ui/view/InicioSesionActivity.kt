package com.hevaz.pruebagruposal.ui.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.widget.PopupMenu
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.room.Room
import com.hevaz.pruebagruposal.R
import com.hevaz.pruebagruposal.data.local.AppDatabase
import com.hevaz.pruebagruposal.data.local.di.DatabaseManager
import com.hevaz.pruebagruposal.data.repository.UserRepository
import com.hevaz.pruebagruposal.databinding.ActivityMainBinding
import com.hevaz.pruebagruposal.network.RetrofitClient
import com.hevaz.pruebagruposal.utils.getAuthToken
import com.hevaz.pruebagruposal.utils.hide
import com.hevaz.pruebagruposal.utils.show


class InicioSesionActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController


        setupSmoothBottomMenu()
        observeDestinationChange()


    }

    private fun setupSmoothBottomMenu() {
        val popupmenu = PopupMenu(this, null)
        popupmenu.inflate(R.menu.menu_botom)
        val menu: Menu = popupmenu.getMenu()
        binding.bottomBar.setupWithNavController(menu, navController)
    }


    private fun observeDestinationChange() {




        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when(destination.id) {
                R.id.loginFragment -> {
                    binding.bottomBar.hide()
                }

                R.id.registerFragment -> {
                    binding.bottomBar.hide()
                }

                R.id.homeScreen -> {
                    binding.bottomBar.show()
                }
                R.id.introFragment2 -> {
                    binding.bottomBar.hide()
                }
                else -> {
                    binding.bottomBar.show()
                }
            }
        }
    }
    fun hideSmoothBottomBar() {
        // oculta el SmoothBottomBar
        binding.bottomBar.hide()

    }

    fun showSmooth() {
        // oculta el SmoothBottomBar
        binding.bottomBar.show()

    }
}
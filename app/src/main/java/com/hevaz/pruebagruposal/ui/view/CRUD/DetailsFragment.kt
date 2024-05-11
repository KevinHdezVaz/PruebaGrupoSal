package com.hevaz.pruebagruposal.ui.view.CRUD

import android.R
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import android.widget.Toolbar
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.hevaz.pruebagruposal.data.local.AppDatabase
import com.hevaz.pruebagruposal.databinding.FragmentDetailsBinding
import com.hevaz.pruebagruposal.network.RetrofitClient
import com.hevaz.pruebagruposal.ui.viewmodel.CRUD.UserViewModel
import com.hevaz.pruebagruposal.ui.viewmodel.CRUD.UserViewModelFactory
import com.hevaz.pruebagruposal.utils.Resource
import com.hevaz.pruebagruposal.utils.animacionProgress
import com.squareup.picasso.Picasso


class DetailsFragment : Fragment() {
    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!

    private val viewModel: UserViewModel by viewModels {
        val dao = AppDatabase.getDatabase(requireContext()).userDao()
        val apiService = RetrofitClient.apiService
        UserViewModelFactory(apiService, dao)
    }

    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        observeUserDetails()

        setupToolbar()


    }

    private fun setupToolbar() {
        val toolbar = binding.toolbar
        (activity as? AppCompatActivity)?.setSupportActionBar(toolbar)
        toolbar.setTitle("Detalles")
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(true)
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayShowHomeEnabled(true)
        toolbar.setNavigationOnClickListener {
            findNavController().navigateUp() // Navegar hacia atrás
        }
    }


    private fun sendCorreo(emailAddress: String) {
        val intent = Intent(Intent.ACTION_SEND).apply {
            type = "message/rfc822"
            putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
            putExtra(Intent.EXTRA_SUBJECT, "Prueba de Correo Kevin Hdez")
            putExtra(Intent.EXTRA_TEXT, "Este es un correo de prueba.")
        }

        try {
            startActivity(Intent.createChooser(intent, "Enviar correo..."))
        } catch (ex: android.content.ActivityNotFoundException) {
            Toast.makeText(context, "No hay aplicaciones de correo instaladas.", Toast.LENGTH_SHORT).show()
        }
    }


    private fun observeUserDetails() {
        viewModel.getUserDetails(args.userId).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    animacionProgress.esconderCarga()

                    resource.data?.let { user ->
                        binding.profileNameTextView.text = "${user.first_name} ${user.last_name}"
                        binding.profileEmailTextView.text = user.email
                        binding.idTextview.text= user.id.toString()
                        Picasso.get()
                            .load(user.avatar)
                            .placeholder(com.hevaz.pruebagruposal.R.drawable.placeholer)
                             .into(binding.profileImageView)

                        // Set the email button click listener here
                        binding.btncorreo.setOnClickListener {
                            sendCorreo(user.email)
                        }

                    }
                }
                Resource.Status.ERROR -> {
                    animacionProgress.esconderCarga()
                    Toast.makeText(context, resource.message ?: "Unknown error", Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> {
                    animacionProgress.mostrarCarga(requireContext())
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.hevaz.pruebagruposal.ui.view.CRUD

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hevaz.pruebagruposal.R
import com.hevaz.pruebagruposal.data.local.AppDatabase
import com.hevaz.pruebagruposal.data.repository.UserRepository
import com.hevaz.pruebagruposal.databinding.FragmentHomeScreenBinding
import com.hevaz.pruebagruposal.databinding.FragmentLoginBinding
import com.hevaz.pruebagruposal.network.RetrofitClient
import com.hevaz.pruebagruposal.ui.adapter.CRUD.UserAdapter
import com.hevaz.pruebagruposal.ui.viewmodel.CRUD.UserViewModel
import com.hevaz.pruebagruposal.ui.viewmodel.CRUD.UserViewModelFactory
import com.hevaz.pruebagruposal.ui.viewmodel.Registro.AuthViewModel
import com.hevaz.pruebagruposal.utils.Resource
import com.hevaz.pruebagruposal.utils.animacionProgress


class HomeScreen : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!
    private val viewModel: UserViewModel by viewModels {
        val dao = AppDatabase.getDatabase(requireContext()).userDao()
        UserViewModelFactory(RetrofitClient.apiService, dao)
    }


    private lateinit var userAdapter: UserAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()


        observeViewModel()
    }
    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.recyclerViewUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }


    private fun observeViewModel() {
        viewModel.getUsers(1).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    animacionProgress.esconderCarga()
                    if (resource.data != null) {
                        userAdapter.submitList(resource.data.data) // Asegúrate de que esto es accesible y correcto.
                    }
                }
                Resource.Status.ERROR -> {
                    animacionProgress.esconderCarga()
                    Toast.makeText(context, resource.message, Toast.LENGTH_LONG).show()
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
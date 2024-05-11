package com.hevaz.pruebagruposal.ui.view.CRUD

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hevaz.pruebagruposal.R
import com.hevaz.pruebagruposal.data.local.AppDatabase
import com.hevaz.pruebagruposal.data.local.User
import com.hevaz.pruebagruposal.data.repository.UserRepository
import com.hevaz.pruebagruposal.databinding.FragmentHomeScreenBinding
import com.hevaz.pruebagruposal.databinding.FragmentLoginBinding
import com.hevaz.pruebagruposal.network.RetrofitClient
import com.hevaz.pruebagruposal.ui.adapter.CRUD.UserAdapter
import com.hevaz.pruebagruposal.ui.viewmodel.CRUD.UserViewModel
import com.hevaz.pruebagruposal.ui.viewmodel.CRUD.UserViewModelFactory
import com.hevaz.pruebagruposal.ui.viewmodel.Registro.AuthViewModel
import com.hevaz.pruebagruposal.utils.Resource
import com.hevaz.pruebagruposal.utils.StickeyHeaderItemDecoration
import com.hevaz.pruebagruposal.utils.animacionProgress
import com.hevaz.pruebagruposal.utils.getUserName
import com.hevaz.pruebagruposal.utils.saveAuthToken
import com.squareup.picasso.Picasso


class HomeScreen : Fragment() {
    private var _binding: FragmentHomeScreenBinding? = null
    private val binding get() = _binding!!


    private val viewModel: UserViewModel by viewModels {
        val dao = AppDatabase.getDatabase(requireContext()).userDao()
        val apiService = RetrofitClient.apiService
        UserViewModelFactory(apiService, dao)
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
        setupToolbar()
    }

    private fun setupToolbar() {
        val userName = requireContext().getUserName()
         binding.toolbar.setTitle("Bienvenido de nuevo $userName")

    }
    private fun showDeleteConfirmationDialog(user: User) {
        AlertDialog.Builder(requireContext())
            .setTitle("Confirmar eliminación")
            .setMessage("¿Estás seguro de que deseas eliminar a ${user.first_name} ${user.last_name}?")
            .setPositiveButton("Eliminar") { dialog, which ->
                viewModel.deleteUser(user)

                deleteUser(user)
            }
            .setNegativeButton("Cancelar", null)
            .show()
    }

    private fun deleteUser(user: User) {
        // Lógica para eliminar el usuario, por ejemplo, a través de tu ViewModel
    //    viewModel.deleteUser(user)
    }


    private fun setupRecyclerView() {
        userAdapter = UserAdapter().apply {

            onEdit = { user ->
                val action = HomeScreenDirections.actionHomeScreenToEditScreen(user.id)
                findNavController().navigate(action)
            }

            onDelete = { user ->
                showDeleteConfirmationDialog(user)
            }

            onUserClicked = { userId ->
                val action = HomeScreenDirections.actionHomeScreenToUserDetailsFragment(userId)
                findNavController().navigate(action)
            }
        }

        binding.recyclerViewUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
            if (itemDecorationCount == 0) {
                addItemDecoration(StickeyHeaderItemDecoration(context) { position ->
                    userAdapter.getHeaderForPosition(position)
                })
            }

            val itemTouchHelper = ItemTouchHelper(userAdapter.simpleItemTouchCallback)
            itemTouchHelper.attachToRecyclerView(binding.recyclerViewUsers)


        }
    }


    private fun observeViewModel() {
        viewModel.fetchUsers(1).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    animacionProgress.esconderCarga()
                    resource.data?.let { users ->
                        userAdapter.submitList(users.toList()) // Convirtiendo la lista si es necesario
                    }
                }
                Resource.Status.ERROR -> {
                    animacionProgress.esconderCarga()
                    Toast.makeText(context, resource.message ?: "Unknown error", Toast.LENGTH_LONG).show()
                }
                Resource.Status.LOADING -> animacionProgress.mostrarCarga(requireContext())
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
package com.hevaz.pruebagruposal.ui.view.CRUD

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
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
        setupToolbar()
    }

    private fun setupToolbar() {
        val userName = requireContext().getUserName()
         binding.toolbar.setTitle(userName)

    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter().apply {
            onUserClicked = { userId ->
                val action = HomeScreenDirections.actionHomeScreenToUserDetailsFragment(userId)
                findNavController().navigate(action)
            }
        }

        binding.recyclerViewUsers.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
            if (itemDecorationCount == 0) {
                val headerDecoration = StickeyHeaderItemDecoration(context) { position ->
                    userAdapter.getHeaderForPosition(position)
                }
                addItemDecoration(headerDecoration)
            }
        }

    }

    private fun observeViewModel() {
        viewModel.getUsers(1).observe(viewLifecycleOwner) { resource ->
            when (resource.status) {
                Resource.Status.SUCCESS -> {
                    animacionProgress.esconderCarga()
                    resource.data?.data?.let { users ->
                        val sortedUsers = users.sortedBy { it.first_name }
                        userAdapter.submitList(sortedUsers)
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
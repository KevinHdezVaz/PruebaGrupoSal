package com.hevaz.pruebagruposal.ui.view.CRUD

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.hevaz.pruebagruposal.data.local.AppDatabase
import com.hevaz.pruebagruposal.databinding.FragmentSearchBinding
import com.hevaz.pruebagruposal.network.RetrofitClient
import com.hevaz.pruebagruposal.ui.adapter.CRUD.UserAdapter
import com.hevaz.pruebagruposal.ui.viewmodel.CRUD.UserViewModel
import com.hevaz.pruebagruposal.ui.viewmodel.CRUD.UserViewModelFactory
import com.hevaz.pruebagruposal.utils.Resource
import com.hevaz.pruebagruposal.utils.animacionProgress
class SearchScreen : Fragment() {
    private var _binding: FragmentSearchBinding? = null
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
        _binding = FragmentSearchBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupRecyclerView()
        setupSearchView()
        observeViewModel()
    }

    private fun setupRecyclerView() {
        userAdapter = UserAdapter()
        binding.recyclerView.apply {
            adapter = userAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

    private fun setupSearchView() {
        binding.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                newText?.let { viewModel.filterUsers(it) }
                return true
            }
        })
    }

    private fun observeViewModel() {
        viewModel.filteredUsers.observe(viewLifecycleOwner) { users ->
            userAdapter.submitList(users)
        }
    }
}

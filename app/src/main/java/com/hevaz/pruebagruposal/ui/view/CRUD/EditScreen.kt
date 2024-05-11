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
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hevaz.pruebagruposal.R
import com.hevaz.pruebagruposal.data.local.AppDatabase
import com.hevaz.pruebagruposal.data.local.User
import com.hevaz.pruebagruposal.data.repository.UserRepository
import com.hevaz.pruebagruposal.databinding.FragmentEditScreenBinding
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


class EditScreen : Fragment() {
    private var _binding: FragmentEditScreenBinding? = null
    private val binding get() = _binding!!
    private val args: EditScreenArgs by navArgs()
    private lateinit var viewModel: UserViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentEditScreenBinding.inflate(inflater, container, false)
        val userDao = AppDatabase.getDatabase(requireContext()).userDao()
        val viewModelFactory = UserViewModelFactory(RetrofitClient.apiService, userDao)
        viewModel = ViewModelProvider(this, viewModelFactory).get(UserViewModel::class.java)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.getUserDetails(args.userId).observe(viewLifecycleOwner) { resource ->
            if (resource.status == Resource.Status.SUCCESS) {
                resource.data?.let { user ->
                    binding.editTextEmail.setText(user.email)
                    binding.editTextFirstName.setText(user.first_name)
                    binding.editTextLastName.setText(user.last_name)
                    binding.editTextAvatar.setText(user.avatar)
                }
            }
        }

        binding.btnSave.setOnClickListener {
            val updatedUser = User(
                id = args.userId,
                email = binding.editTextEmail.text.toString(),
                first_name = binding.editTextFirstName.text.toString(),
                last_name = binding.editTextLastName.text.toString(),
                avatar = binding.editTextAvatar.text.toString()
            )
            viewModel.updateUser(updatedUser)
            Toast.makeText(context, "Datos actualizados correctamente", Toast.LENGTH_SHORT).show()
         findNavController().popBackStack()  // Regresa al fragmento anterior tras guardar los cambios
        }

        binding.btnCancel.setOnClickListener {
            findNavController().popBackStack()  // Regresa sin hacer cambios
        }
    }




    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}

package com.hevaz.pruebagruposal.ui.view.Profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.hevaz.pruebagruposal.R
import com.hevaz.pruebagruposal.data.local.User
import com.hevaz.pruebagruposal.databinding.FragmentLoginBinding
import com.hevaz.pruebagruposal.databinding.FragmentProfileBinding
import com.hevaz.pruebagruposal.ui.viewmodel.Registro.AuthViewModel
 import com.hevaz.pruebagruposal.utils.Resource
import com.hevaz.pruebagruposal.utils.animacionProgress
import com.hevaz.pruebagruposal.utils.clearAuthToken
import com.hevaz.pruebagruposal.utils.clearName
import com.hevaz.pruebagruposal.utils.getAuthToken
import com.hevaz.pruebagruposal.utils.hideKeyboard
import com.hevaz.pruebagruposal.utils.saveAuthToken
import com.squareup.picasso.Picasso


class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        displayUserInfo()


      binding.logout.setOnClickListener {
          context?.clearAuthToken()
          context?.clearName()

          findNavController().navigate(R.id.action_profileFragment_to_loginFragment,  )

      }
    }

    private fun displayUserInfo() {
         val user = fetchLoggedInUser()
        //binding.profileNameTextView.text = "${user.firstName} ${user.lastName}"
        binding.profileEmailTextView.text = user.email
         Picasso.get().load(user.avatar).into(binding.profileImageView)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchLoggedInUser(): User {
        // Aquí deberías obtener los datos del usuario logueado
        return User(1, "Doe", "johndoe@example.com", "sdfasdfa","asdfasd")
    }
}
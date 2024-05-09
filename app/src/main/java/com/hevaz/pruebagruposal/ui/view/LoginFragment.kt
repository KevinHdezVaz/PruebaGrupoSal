package com.hevaz.pruebagruposal.ui.view

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.hevaz.pruebagruposal.R
import com.hevaz.pruebagruposal.databinding.FragmentLoginBinding
import com.hevaz.pruebagruposal.ui.viewmodel.Registro.AuthViewModel
 import com.hevaz.pruebagruposal.utils.Resource
import com.hevaz.pruebagruposal.utils.animacionProgress
import com.hevaz.pruebagruposal.utils.getAuthToken
import com.hevaz.pruebagruposal.utils.hideKeyboard
import com.hevaz.pruebagruposal.utils.saveAuthToken
import com.hevaz.pruebagruposal.utils.saveName


class LoginFragment : Fragment() {
    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentLoginBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isUserLoggin()
        duLogin()
        GotosignUp()
        binding.btnprueba.setOnClickListener {
            binding.editTextPassword.setText("cityslicka")
            binding.editTextEmail.setText("eve.holt@reqres.in")


        }


        binding.textoaRegistro.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
        }
    }

    fun isUserLoggin() {
        /**
         * Si el usuario essta logueado (diferente de nulo) pasar al home Fragment
         * */

        if (!requireContext().getAuthToken().isNullOrEmpty()) {
             findNavController().navigate(R.id.action_loginFragment_to_homeScreen)

        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    fun duLogin() {
        binding.btnSignin.setOnClickListener {

            it.hideKeyboard()

            val email = binding.editTextEmail.text.toString().trim()
            val password = binding.editTextPassword.text.toString().trim()
            validateCredentials(requireContext(), email, password)
              loginUser(email,password)
        }
    }

    private fun validateCredentials(context: Context, email: String, password: String) {
        if (email.isEmpty()) {
            binding.editTextEmail.error = "Email esta vacio"
            return
        }


        if (password.isEmpty()) {
            binding.editTextPassword.error = "Email esta vacio"
            return
        }
    }


    private fun GotosignUp() {

        binding.txtSignup.setOnClickListener {
            findNavController().navigate(R.id.action_loginFragment_to_registerFragment)

        }
    }


    private fun loginUser(email: String, password: String) {
        viewModel.login(email, password).observe(viewLifecycleOwner) { result ->

            when (result.status) {
                Resource.Status.LOADING -> {
                    animacionProgress.mostrarCarga(requireContext())

                }

                Resource.Status.SUCCESS -> {
                    animacionProgress.esconderCarga()

                    //guardar token
                    result.data?.token?.let {
                        requireContext().saveAuthToken(it)
                        requireContext().saveName(email)


                        Toast.makeText(
                            requireContext(),
                            " Token guardado: ${it}",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                     findNavController().navigate(R.id.action_loginFragment_to_homeScreen)
                    Toast.makeText(
                        requireContext(),
                        "Login Successful! Token: ${result.data?.token}",
                        Toast.LENGTH_SHORT
                    ).show()


                }

                Resource.Status.ERROR -> {
                    animacionProgress.esconderCarga()
                    Toast.makeText(requireContext(), result.message ?: "Error desconocido", Toast.LENGTH_LONG).show()



                }

            }
        }
    }
}
package com.hevaz.pruebagruposal.ui.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.hevaz.pruebagruposal.R
import com.hevaz.pruebagruposal.databinding.FragmentRegisterBinding
import com.hevaz.pruebagruposal.ui.viewmodel.Registro.AuthViewModel
import com.hevaz.pruebagruposal.utils.Resource
import com.hevaz.pruebagruposal.utils.animacionProgress.Companion.esconderCarga
import com.hevaz.pruebagruposal.utils.animacionProgress.Companion.mostrarCarga
import com.hevaz.pruebagruposal.utils.validateRegisterSignUp.Companion.validateEmail
import com.hevaz.pruebagruposal.utils.validateRegisterSignUp.Companion.validateForm
import com.hevaz.pruebagruposal.utils.validateRegisterSignUp.Companion.validatePassword


class RegisterFragment : Fragment() {
    private var _binding: FragmentRegisterBinding? = null
    private val binding get() = _binding!!
    private val viewModel: AuthViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRegisterBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getSignUp()
        binding.btnprueba.setOnClickListener {
            binding.editTextPassword.setText("pistol")
            binding.editTextConfirmPassword.setText("pistol")
            binding.editTextEmail.setText("eve.holt@reqres.in")
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun getSignUp(){


        binding.btnSignUp.setOnClickListener {
             val password = binding.editTextPassword.text.toString().trim()
            val confirmpassword = binding.editTextConfirmPassword.text.toString().trim()
            val email = binding.editTextEmail.text.toString().trim()


            if(password != confirmpassword){
                binding.editTextConfirmPassword.error = "Las contraseñas no coinciden."
                binding.editTextPassword.error = "Las contraseñas no coinciden."
                return@setOnClickListener //retorna para digitar
            }

         //   if(!validateEmail(email)) binding.editTextEmail.error ="Este email no es valido"
          //  if(!validatePassword(password)) binding.editTextPassword.error="You Better your password"
           // if(validateForm(email,password, passwordConfirmation = confirmpassword))



            createuser(email,password)



        }

    }

    private fun createuser(email: String, password: String ) {
        viewModel.register(email, password).observe(viewLifecycleOwner) { result ->

            when (result.status) {
                Resource.Status.LOADING -> {
                  mostrarCarga(requireContext())

                }

                Resource.Status.SUCCESS -> {
                     esconderCarga()
                   findNavController().navigate(R.id.action_registerFragment_to_homeScreen)
                    Toast.makeText(
                        requireContext(),
                        "Registration Successful! Token: ${result.data?.token}",
                        Toast.LENGTH_SHORT
                    ).show()


                }

                Resource.Status.ERROR -> {
                     esconderCarga()
                    Toast.makeText(requireContext(), result.message ?: "Error desconocido", Toast.LENGTH_LONG).show()



                }

            }
        }
    }
}
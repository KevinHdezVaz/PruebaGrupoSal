package com.hevaz.pruebagruposal.ui.viewmodel.Registro

import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.hevaz.pruebagruposal.data.repository.AuthRepository
import com.hevaz.pruebagruposal.utils.Resource
import kotlinx.coroutines.Dispatchers


class AuthViewModel : ViewModel() {
    fun register(email: String, password: String? = null) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val response = AuthRepository.registerUser(email, password)
            emitSource(response)
        } catch (e: Exception) {
            emit(Resource.error("Error de registro: ${e.localizedMessage}", null))  // Emite un error con un mensaje
        }
    }

    fun login(email: String, password: String) = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        try {
            val response = AuthRepository.loginUser(email, password)
            emitSource(response)
        } catch (e: Exception) {
            emit(Resource.error("Error de registro: ${e.localizedMessage}", null))  // Emite un error con un mensaje
        }
    }
}
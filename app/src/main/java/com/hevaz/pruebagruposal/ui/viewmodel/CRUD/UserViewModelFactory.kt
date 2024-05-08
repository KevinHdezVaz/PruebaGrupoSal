package com.hevaz.pruebagruposal.ui.viewmodel.CRUD

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hevaz.pruebagruposal.data.repository.UserRepository

class UserViewModelFactory(private val userRepository: UserRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(userRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

package com.hevaz.pruebagruposal.ui.viewmodel.CRUD

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.hevaz.pruebagruposal.data.local.DAO.UserDao
import com.hevaz.pruebagruposal.data.repository.UserRepository
import com.hevaz.pruebagruposal.network.ApiService
class UserViewModelFactory(
    private val apiService: ApiService,
    private val userDao: UserDao
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(UserViewModel::class.java)) {
            return UserViewModel(UserRepository(apiService, userDao)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

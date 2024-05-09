package com.hevaz.pruebagruposal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.hevaz.pruebagruposal.data.local.DAO.UserDao
import com.hevaz.pruebagruposal.data.local.User
import com.hevaz.pruebagruposal.data.model.Auth.ApiError
 import com.hevaz.pruebagruposal.data.model.CRUD.UserListResponse
 import com.hevaz.pruebagruposal.network.ApiService
import com.hevaz.pruebagruposal.network.RetrofitClient
import com.hevaz.pruebagruposal.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext




class UserRepository(private val apiService: ApiService, private val userDao: UserDao) {
    suspend fun fetchUsers(page: Int): LiveData<Resource<List<User>>> {
        val result = MediatorLiveData<Resource<List<User>>>()

        try {
            val response = apiService.getUsers(page)
            if (response.isSuccessful) {
                response.body()?.data?.let { users ->
                    userDao.insertAll(users) // Guardar en la base de datos local
                }
            } else {
                result.postValue(Resource.error("Error: ${response.message()}", null))
                return result
            }
        } catch (e: Exception) {
            result.postValue(Resource.error("Network error: ${e.localizedMessage}", null))
            return result
        }

        // Observa los usuarios de la base de datos y actualiza los datos en vivo
        result.addSource(userDao.getAllUsers()) { users ->
            result.value = Resource.success(users)
        }
        return result
    }
}


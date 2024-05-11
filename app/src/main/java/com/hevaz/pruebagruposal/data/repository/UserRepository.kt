package com.hevaz.pruebagruposal.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import com.hevaz.pruebagruposal.data.local.DAO.UserDao
import com.hevaz.pruebagruposal.data.local.User
import com.hevaz.pruebagruposal.data.model.Auth.ApiError
import com.hevaz.pruebagruposal.data.model.CRUD.UserDetail
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
                    userDao.insertAll(users)

                    result.postValue(Resource.success(users))
                }
            } else {
                result.postValue(Resource.error("Error: ${response.message()}", null))
            }
        } catch (e: Exception) {

        }

        result.addSource(userDao.getAllUsers()) { users ->
            result.postValue(Resource.success(users))
        }
        return result
    }

    suspend fun getUserDetails(userId: Int): Resource<UserDetail> {
        return try {
            val response = apiService.getUserById(userId)
            if (response.isSuccessful) {
                response.body()?.data?.let { return Resource.success(it) }
                Resource.error("No user found", null)
            } else {
                Resource.error("Error fetching user: ${response.message()}", null)
            }
        } catch (e: Exception) {
            Resource.error("Error fetching user: ${e.message}", null)
        }
    }

    suspend fun updateUser(user: User): Resource<User> = withContext(Dispatchers.IO) {
        try {
            // Actualizar en Room
            userDao.updateUser(user)
            // Simular actualización en la API
            val response = apiService.updateUser(user.id, user) // Asumiendo que tu API tiene un endpoint para actualizar
            if (response.isSuccessful) {
                Log.d("UserRepository", "API Update simulated: $user")
            } else {
                Log.e("UserRepository", "API Update failed: ${response.errorBody()?.string()}")
            }
            Resource.success(user)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error updating user", e)
            Resource.error("Update failed: ${e.message}", null)
        }
    }

    suspend fun deleteUser(user: User): Resource<User> = withContext(Dispatchers.IO) {
        try {
            // Actualizar en Room
            userDao.deleteUser(user)


            val response = apiService.updateUser(user.id, user) // Asumiendo que tu API tiene un endpoint para actualizar
            if (response.isSuccessful) {
                Log.d("UserRepository", "API Update simulated: $user")
            } else {
                Log.e("UserRepository", "API Update failed: ${response.errorBody()?.string()}")
            }
            Resource.success(user)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error updating user", e)
            Resource.error("Update failed: ${e.message}", null)
        }
    }
    suspend fun createuser(user: User): Resource<User> = withContext(Dispatchers.IO) {
        try {
            // Actualizar en Room
            userDao.deleteUser(user)


            val response = apiService.updateUser(user.id, user) // Asumiendo que tu API tiene un endpoint para actualizar
            if (response.isSuccessful) {
                Log.d("UserRepository", "API Update simulated: $user")
            } else {
                Log.e("UserRepository", "API Update failed: ${response.errorBody()?.string()}")
            }
            Resource.success(user)
        } catch (e: Exception) {
            Log.e("UserRepository", "Error updating user", e)
            Resource.error("Update failed: ${e.message}", null)
        }
    }
}


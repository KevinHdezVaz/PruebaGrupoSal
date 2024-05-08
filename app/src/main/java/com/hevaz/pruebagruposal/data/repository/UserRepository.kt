package com.hevaz.pruebagruposal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.hevaz.pruebagruposal.data.model.Auth.ApiError
import com.hevaz.pruebagruposal.data.model.CRUD.UserListResponse
 import com.hevaz.pruebagruposal.network.ApiService
import com.hevaz.pruebagruposal.network.RetrofitClient
import com.hevaz.pruebagruposal.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext



class UserRepository(private val apiService: ApiService) {
    suspend fun fetchUsers(page: Int): LiveData<Resource<UserListResponse>> {
        val response = try {
            apiService.getUsers(page)
        } catch (e: Exception) {
            return MutableLiveData(Resource.error("Network error", null))
        }
        return if (response.isSuccessful) {
            MutableLiveData(Resource.success(response.body()))
        } else {
            MutableLiveData(Resource.error("Error: ${response.message()}", null))
        }
    }
}


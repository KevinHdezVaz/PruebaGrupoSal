package com.hevaz.pruebagruposal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.gson.Gson
import com.hevaz.pruebagruposal.data.model.Auth.ApiError
import com.hevaz.pruebagruposal.data.model.Auth.LoginResponse
import com.hevaz.pruebagruposal.data.model.Auth.RegistroResponse
import com.hevaz.pruebagruposal.data.model.Auth.UserRegistrationRequest
import com.hevaz.pruebagruposal.network.RetrofitClient
import com.hevaz.pruebagruposal.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object AuthRepository {

    suspend fun registerUser(email: String, password: String? = null): LiveData<Resource<RegistroResponse>> {
        val result = MutableLiveData<Resource<RegistroResponse>>()
        result.postValue(Resource.loading(null))

        try {
            val response = withContext(Dispatchers.IO) {
                RetrofitClient.apiService.registerUser(UserRegistrationRequest(email, password))
            }
            if (response.isSuccessful && response.body() != null) {
                result.postValue(Resource.success(response.body()!!))
            } else {
                val errorResponse = Gson().fromJson(response.errorBody()?.charStream(), ApiError::class.java)
                result.postValue(Resource.error(errorResponse.error ?: "Unknown error", null))
            }
        } catch (e: Exception) {
            result.postValue(Resource.error("Network error: ${e.localizedMessage}", null))
        }

        return result
    }


    suspend fun loginUser(email: String, password: String? = null): LiveData<Resource<LoginResponse>> {
        val result = MutableLiveData<Resource<LoginResponse>>()
        result.postValue(Resource.loading(null))

        try {
            val response = withContext(Dispatchers.IO) {
                RetrofitClient.apiService.loginUser(UserRegistrationRequest(email, password))
            }
            if (response.isSuccessful && response.body() != null) {
                result.postValue(Resource.success(response.body()!!))
            } else {
                val errorResponse = Gson().fromJson(response.errorBody()?.charStream(), ApiError::class.java)
                result.postValue(Resource.error(errorResponse.error ?: "Unknown error", null))
            }
        } catch (e: Exception) {
            result.postValue(Resource.error("Network error: ${e.localizedMessage}", null))
        }

        return result
    }
}
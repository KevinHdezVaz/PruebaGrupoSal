package com.hevaz.pruebagruposal.ui.viewmodel.CRUD

 import androidx.lifecycle.LiveData
 import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
 import androidx.lifecycle.ViewModelProvider
 import androidx.lifecycle.viewModelScope
  import com.hevaz.pruebagruposal.network.RetrofitClient
 import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
 import androidx.lifecycle.liveData
 import com.hevaz.pruebagruposal.data.model.CRUD.UserDetail
 import com.hevaz.pruebagruposal.data.repository.AuthRepository
 import com.hevaz.pruebagruposal.data.repository.UserRepository
 import com.hevaz.pruebagruposal.network.RetrofitClient.apiService
 import com.hevaz.pruebagruposal.utils.Resource


class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUsers(page: Int) = liveData {
        emit(Resource.loading(null))
        try {
            val response = apiService.getUsers(page)
            if (response.isSuccessful && response.body() != null) {
                emit(Resource.success(response.body()))
            } else {
                emit(Resource.error("Error al obtener datos: ${response.message()}",null))
            }
        } catch (e: Exception) {
            emit(Resource.error("Error de red: ${e.localizedMessage}", null))  // Emite un error con un mensaje
        }
    }

    fun getUserDetails(userId: Int): LiveData<Resource<UserDetail>> {
        val result = MutableLiveData<Resource<UserDetail>>()
        viewModelScope.launch {
            result.value = userRepository.getUserDetails(userId)
        }
        return result
    }


}

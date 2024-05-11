package com.hevaz.pruebagruposal.ui.viewmodel.CRUD

 import androidx.lifecycle.LiveData
 import androidx.lifecycle.MediatorLiveData
 import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
 import androidx.lifecycle.ViewModelProvider
 import androidx.lifecycle.viewModelScope
  import com.hevaz.pruebagruposal.network.RetrofitClient
 import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
 import androidx.lifecycle.liveData
 import com.hevaz.pruebagruposal.data.local.DAO.UserDao
 import com.hevaz.pruebagruposal.data.local.User
 import com.hevaz.pruebagruposal.data.model.CRUD.UserDetail
 import com.hevaz.pruebagruposal.data.repository.AuthRepository
 import com.hevaz.pruebagruposal.data.repository.UserRepository
 import com.hevaz.pruebagruposal.network.RetrofitClient.apiService
 import com.hevaz.pruebagruposal.utils.Resource


class UserViewModel(private val userRepository: UserRepository ) : ViewModel() {

    private val _userData = MutableLiveData<Resource<User>>()
    val userData: LiveData<Resource<User>> = _userData
    private val _users = MutableLiveData<List<User>>()
    val users: LiveData<List<User>> = _users

    var allUsers: LiveData<List<User>> = userRepository.getAllUsers()

    fun filterUsers(query: String) {
        allUsers = if (query.isEmpty()) {
            userRepository.getAllUsers()
        } else {
            userRepository.getUsersByName(query)
        }
    }
    fun fetchUsers(page: Int): LiveData<Resource<List<User>>> = liveData(Dispatchers.IO) {
        emit(Resource.loading())
        val result = userRepository.fetchUsers(page)
        emitSource(result)


    }

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
            emit(Resource.error("Error de red: ${e.localizedMessage}", null))
        }
    }

    fun getUserDetails(userId: Int): LiveData<Resource<UserDetail>> {
        val result = MutableLiveData<Resource<UserDetail>>()
        viewModelScope.launch {
            result.value = userRepository.getUserDetails(userId)
        }
        return result
    }


    // Función para actualizar un usuario en la base de datos local

    fun updateUser(user: User) = viewModelScope.launch {
        _userData.postValue(Resource.loading())
        val result = userRepository.updateUser(user)
        _userData.postValue(result)
    }




    // Función para eliminar un usuario de la base de datos local

    fun deleteUser(user: User) = viewModelScope.launch {
        val result = userRepository.deleteUser(user)
        if (result.status == Resource.Status.SUCCESS) {
            fetchUsers(1)  // Recargar los usuarios después de borrar
        }
    }
    // Función para agregar un nuevo usuario a la base de datos local
    fun addUser(user: User) {
        viewModelScope.launch(Dispatchers.IO) {
         //   userRepository.addUser(user)
        }
    }


}

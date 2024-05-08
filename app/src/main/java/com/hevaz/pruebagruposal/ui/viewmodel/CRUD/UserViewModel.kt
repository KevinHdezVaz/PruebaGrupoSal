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
 import com.hevaz.pruebagruposal.data.repository.AuthRepository
 import com.hevaz.pruebagruposal.data.repository.UserRepository
 import com.hevaz.pruebagruposal.utils.Resource


class UserViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun getUsers(page: Int) = liveData {
        emit(Resource.loading())
        emitSource(userRepository.fetchUsers(page))
    }


}

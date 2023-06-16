package com.glendito.fruisca.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glendito.fruisca.database.entities.UserEntity
import com.glendito.fruisca.repositories.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class RegisterViewModel(
    private val userRepository: UserRepository
) : ViewModel() {

    private val isRegisteredEvent = MutableLiveData<Boolean>()
    val isRegistered = isRegisteredEvent as LiveData<Boolean>

    fun register(userEntity: UserEntity) {
        viewModelScope.launch {
            delay(500)
            isRegisteredEvent.postValue(userRepository.register(userEntity))
        }
    }
}
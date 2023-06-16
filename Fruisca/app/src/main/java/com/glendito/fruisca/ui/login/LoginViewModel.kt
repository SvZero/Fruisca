package com.glendito.fruisca.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glendito.fruisca.preferences.UserPreferences
import com.glendito.fruisca.repositories.UserRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
): ViewModel() {

    private val isSuccessLoginEvent = MutableLiveData<Boolean>()
    val isSuccessLogin = isSuccessLoginEvent as LiveData<Boolean>

    fun login(email: String, password: String) {
        viewModelScope.launch {
            delay(500)
            val isSuccess = userRepository.login(email, password)
            if (isSuccess) {
                userPreferences.setEmail(email)
            }
            isSuccessLoginEvent.postValue(isSuccess)
        }
    }
}
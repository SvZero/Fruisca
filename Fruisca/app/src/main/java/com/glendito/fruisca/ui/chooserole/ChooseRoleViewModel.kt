package com.glendito.fruisca.ui.chooserole

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glendito.fruisca.preferences.UserPreferences
import com.glendito.fruisca.repositories.UserRepository
import kotlinx.coroutines.launch

class ChooseRoleViewModel(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
): ViewModel() {

    private val isUpdatedEvent = MutableLiveData<Boolean>()
    val isUpdated = isUpdatedEvent as LiveData<Boolean>

    fun setRole(role: String) {
        viewModelScope.launch {
            val email = userPreferences.getEmail()
            userRepository.setRole(role, email)
            userPreferences.setRole(role)
            isUpdatedEvent.postValue(true)
        }
    }
}
package com.glendito.fruisca.ui.landing

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glendito.fruisca.preferences.UserPreferences
import com.glendito.fruisca.repositories.UserRepository
import kotlinx.coroutines.launch

class LandingViewModel(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
): ViewModel() {
    private val roleEvent = MutableLiveData<String>()
    val role = roleEvent as LiveData<String>

    fun getEmail(): String = userPreferences.getEmail()
    fun fetchRole() {
        viewModelScope.launch {
            roleEvent.postValue(userRepository.getRole(getEmail()))
        }
    }
}
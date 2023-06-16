package com.glendito.fruisca.ui.home.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glendito.fruisca.database.entities.UserEntity
import com.glendito.fruisca.preferences.UserPreferences
import com.glendito.fruisca.repositories.UserRepository
import kotlinx.coroutines.launch

class ProfileViewModel(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
): ViewModel() {

    private val userEvent = MutableLiveData<UserEntity?>()
    val user = userEvent as LiveData<UserEntity?>

    fun fetchUser() {
        viewModelScope.launch {
            userEvent.postValue(userRepository.getUser(userPreferences.getEmail()))
        }
    }
}
package com.glendito.fruisca.ui.editprofile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glendito.fruisca.database.entities.UserEntity
import com.glendito.fruisca.preferences.UserPreferences
import com.glendito.fruisca.repositories.UserRepository
import kotlinx.coroutines.launch

class EditProfileViewModel(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
) : ViewModel() {

    private val isEditedEvent = MutableLiveData<Boolean>()
    val isEdited = isEditedEvent as LiveData<Boolean>

    private val userEvent = MutableLiveData<UserEntity>()
    val user = userEvent as LiveData<UserEntity>

    fun getProfile() {
        viewModelScope.launch {
            userEvent.postValue(userRepository.getUser(userPreferences.getEmail()))
        }
    }

    fun editProfile(name: String, phone: String, address: String) {
        viewModelScope.launch {
            userRepository.editProfile(name, phone, address)
            isEditedEvent.postValue(true)
        }
    }
}
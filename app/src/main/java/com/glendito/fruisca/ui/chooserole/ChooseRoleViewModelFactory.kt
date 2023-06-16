package com.glendito.fruisca.ui.chooserole

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.glendito.fruisca.preferences.UserPreferences
import com.glendito.fruisca.repositories.UserRepository

class ChooseRoleViewModelFactory(
    private val userRepository: UserRepository,
    private val userPreferences: UserPreferences
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ChooseRoleViewModel(userRepository, userPreferences) as T
    }
}
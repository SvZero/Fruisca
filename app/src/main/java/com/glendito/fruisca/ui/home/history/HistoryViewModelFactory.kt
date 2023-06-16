package com.glendito.fruisca.ui.home.history

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.glendito.fruisca.repositories.FruitRepository

class HistoryViewModelFactory(
    private val fruitRepository: FruitRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return HistoryViewModel(fruitRepository) as T
    }
}
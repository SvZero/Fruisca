package com.glendito.fruisca.ui.resultscan

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.glendito.fruisca.repositories.FruitRepository

class ResultScanViewModelFactory(
    private val fruitRepository: FruitRepository
): ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ResultScanViewModel(fruitRepository) as T
    }
}
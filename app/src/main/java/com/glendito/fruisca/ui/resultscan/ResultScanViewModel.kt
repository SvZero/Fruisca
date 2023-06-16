package com.glendito.fruisca.ui.resultscan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glendito.fruisca.database.entities.FruitEntity
import com.glendito.fruisca.repositories.FruitRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class ResultScanViewModel(
    private val fruitRepository: FruitRepository
): ViewModel() {

    private val scanResultEvent = MutableLiveData<ScanResult>()
    val scanResult = scanResultEvent as LiveData<ScanResult>

    fun uploadFruit(fruit: FruitEntity) {
        viewModelScope.launch {
            fruitRepository.insert(fruit)
            delay(3000)
            scanResultEvent.postValue(ScanResult.Success)
        }
    }
}

sealed interface ScanResult {
    object Success : ScanResult
    object Failed : ScanResult
}
package com.glendito.fruisca.ui.home.history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glendito.fruisca.database.entities.FruitEntity
import com.glendito.fruisca.repositories.FruitRepository
import kotlinx.coroutines.launch

class HistoryViewModel(
    private val fruitRepository: FruitRepository
): ViewModel() {
    private val fruitsEvent = MutableLiveData<List<FruitEntity>>()
    val fruits = fruitsEvent as LiveData<List<FruitEntity>>

    fun getAll() {
        viewModelScope.launch {
            /*val currentFruits = listOf(
                FruitEntity(
                    1,
                    "Apel",
                    80,
                    "Layak Konsumsi",
                    "15 Juni 2023 - 12.45 GMT+7",
                    "data:image/jpeg;base64,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"
                ),
                FruitEntity(
                    1,
                    "Pisang",
                    30,
                    "Tidak Layak Konsumsi",
                    "15 Juni 2023 - 12.45 GMT+7",
                    "https://akcdn.detik.net.id/visual/2015/01/06/3145081d-6a92-4c32-a8d6-065203f5097c_169.jpg?w=650"
                )
            )*/
             fruitsEvent.postValue(fruitRepository.getAll())
            //fruitsEvent.postValue(currentFruits)
        }
    }
}
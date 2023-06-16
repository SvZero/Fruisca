package com.glendito.fruisca.ui.newsdetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glendito.fruisca.database.entities.NewsEntity
import com.glendito.fruisca.repositories.NewsRepository
import kotlinx.coroutines.launch

class NewsDetailViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {

    private val newsEvent = MutableLiveData<NewsEntity>()
    val news = newsEvent as LiveData<NewsEntity>

    fun fetchDetail(id: Int) {
        viewModelScope.launch {
            newsEvent.postValue(newsRepository.fetchDetail(id))
        }
    }
}
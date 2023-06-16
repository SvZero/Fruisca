package com.glendito.fruisca.ui.home.news

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.glendito.fruisca.database.entities.NewsEntity
import com.glendito.fruisca.repositories.NewsRepository
import kotlinx.coroutines.launch

class NewsViewModel(
    private val newsRepository: NewsRepository
): ViewModel() {

    private val newsEvent = MutableLiveData<List<NewsEntity>>()
    val news = newsEvent as LiveData<List<NewsEntity>>

    fun fetchNews() {
        viewModelScope.launch {
            newsEvent.postValue(newsRepository.fetchData())
        }
    }
}
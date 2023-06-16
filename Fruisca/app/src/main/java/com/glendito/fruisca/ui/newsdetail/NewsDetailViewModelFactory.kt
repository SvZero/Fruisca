package com.glendito.fruisca.ui.newsdetail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.glendito.fruisca.repositories.NewsRepository

class NewsDetailViewModelFactory(
    private val newsRepository: NewsRepository
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return NewsDetailViewModel(newsRepository) as T
    }
}
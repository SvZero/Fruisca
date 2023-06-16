package com.glendito.fruisca.ui

import com.glendito.fruisca.preferences.UserPreferences
import com.glendito.fruisca.repositories.FruitRepository
import com.glendito.fruisca.repositories.NewsRepository
import com.glendito.fruisca.repositories.UserRepository
import com.glendito.fruisca.ui.chooserole.ChooseRoleViewModelFactory
import com.glendito.fruisca.ui.editprofile.EditProfileViewModelFactory
import com.glendito.fruisca.ui.home.history.HistoryViewModelFactory
import com.glendito.fruisca.ui.home.news.NewsViewModelFactory
import com.glendito.fruisca.ui.newsdetail.NewsDetailViewModelFactory
import com.glendito.fruisca.ui.home.profile.ProfileViewModelFactory
import com.glendito.fruisca.ui.landing.LandingViewModelFactory
import com.glendito.fruisca.ui.login.LoginViewModelFactory
import com.glendito.fruisca.ui.register.RegisterViewModelFactory
import com.glendito.fruisca.ui.resultscan.ResultScanViewModelFactory
import dagger.Module
import dagger.Provides

@Module
class ViewModelModule {

    @Provides
    fun provideRegisterViewModelFactory(userRepository: UserRepository): RegisterViewModelFactory {
        return RegisterViewModelFactory(userRepository)
    }

    @Provides
    fun provideEditProfileViewModelFactory(userRepository: UserRepository, userPreferences: UserPreferences): EditProfileViewModelFactory {
        return EditProfileViewModelFactory(userRepository, userPreferences)
    }

    @Provides
    fun provideLoginViewModelFactory(
        userRepository: UserRepository,
        userPreferences: UserPreferences
    ): LoginViewModelFactory {
        return LoginViewModelFactory(userRepository, userPreferences)
    }

    @Provides
    fun provideChooseRoleViewModelFactory(
        userRepository: UserRepository,
        userPreferences: UserPreferences
    ): ChooseRoleViewModelFactory {
        return ChooseRoleViewModelFactory(userRepository, userPreferences)
    }

    @Provides
    fun provideProfileViewModelFactory(
        userRepository: UserRepository,
        userPreferences: UserPreferences
    ): ProfileViewModelFactory {
        return ProfileViewModelFactory(userRepository, userPreferences)
    }

    @Provides
    fun provideLandingViewModelFactory(
        userRepository: UserRepository,
        userPreferences: UserPreferences
    ): LandingViewModelFactory {
        return LandingViewModelFactory(userRepository, userPreferences)
    }

    @Provides
    fun provideNewsViewModelFactory(newsRepository: NewsRepository): NewsViewModelFactory {
        return NewsViewModelFactory(newsRepository)
    }

    @Provides
    fun provideNewsDetailViewModelFactory(newsRepository: NewsRepository): NewsDetailViewModelFactory {
        return NewsDetailViewModelFactory(newsRepository)
    }

    @Provides
    fun provideResultScanViewModelFactory(fruitRepository: FruitRepository): ResultScanViewModelFactory {
        return ResultScanViewModelFactory(fruitRepository)
    }

    @Provides
    fun provideHistoryViewModelFactory(fruitRepository: FruitRepository): HistoryViewModelFactory {
        return HistoryViewModelFactory(fruitRepository)
    }

}
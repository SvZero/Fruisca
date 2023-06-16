package com.glendito.fruisca.app

import com.glendito.fruisca.database.DatabaseModule
import com.glendito.fruisca.network.NetworkModule
import com.glendito.fruisca.repositories.RepositoryModule
import com.glendito.fruisca.ui.landing.LandingActivity
import com.glendito.fruisca.ui.ViewModelModule
import com.glendito.fruisca.ui.chooserole.ChooseRoleActivity
import com.glendito.fruisca.ui.home.news.NewsFragment
import com.glendito.fruisca.ui.newsdetail.NewsDetailActivity
import com.glendito.fruisca.ui.editprofile.EditProfileActivity
import com.glendito.fruisca.ui.home.history.HistoryFragment
import com.glendito.fruisca.ui.home.profile.ProfileFragment
import com.glendito.fruisca.ui.login.LoginActivity
import com.glendito.fruisca.ui.register.RegisterActivity
import com.glendito.fruisca.ui.resultscan.ResultScanActivity
import com.glendito.fruisca.ui.settings.SettingsActivity
import dagger.Component

@Component(
    modules = [
        AppModule::class,
        DatabaseModule::class,
        NetworkModule::class,
        RepositoryModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent {
    fun inject(activity: LandingActivity)
    fun inject(activity: RegisterActivity)
    fun inject(activity: LoginActivity)
    fun inject(activity: ChooseRoleActivity)
    fun inject(activity: NewsDetailActivity)
    fun inject(activity: SettingsActivity)
    fun inject(activity: ResultScanActivity)
    fun inject(activity: EditProfileActivity)

    fun inject(fragment: ProfileFragment)
    fun inject(fragment: NewsFragment)
    fun inject(fragment: HistoryFragment)
}
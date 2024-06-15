package com.vaxapp.thingspeakviewer.di

import com.vaxapp.thingspeakviewer.data.setting.NotificationPreferenceDataRepository
import com.vaxapp.thingspeakviewer.data.setting.NotificationPreferenceDataSource
import com.vaxapp.thingspeakviewer.data.setting.PreferenceStore
import com.vaxapp.thingspeakviewer.domain.NotificationPreferenceRepository
import com.vaxapp.thingspeakviewer.domain.GetNotificationPreferenceUseCase
import com.vaxapp.thingspeakviewer.domain.SetNotificationPreferenceUseCase
import com.vaxapp.thingspeakviewer.view.settings.SettingsViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.module.Module
import org.koin.dsl.module


fun officeWeatherAppModules() = listOf(dataModule, domainModule, viewModule, module)

val module: Module = module {
    single<PreferenceStore> { PreferenceStore(androidContext()) }
    single<NotificationPreferenceDataSource> { NotificationPreferenceDataSource(get()) }
    single<NotificationPreferenceRepository> { NotificationPreferenceDataRepository(get()) }
    single<GetNotificationPreferenceUseCase> { GetNotificationPreferenceUseCase(get()) }
    single<SetNotificationPreferenceUseCase> { SetNotificationPreferenceUseCase(get()) }

    viewModel { SettingsViewModel(get(), get()) }
}

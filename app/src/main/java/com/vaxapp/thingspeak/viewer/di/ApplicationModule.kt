package com.vaxapp.thingspeak.viewer.di

import com.vaxapp.thingspeak.viewer.data.setting.NotificationPreferenceDataRepository
import com.vaxapp.thingspeak.viewer.data.setting.NotificationPreferenceDataSource
import com.vaxapp.thingspeak.viewer.data.setting.PreferenceStore
import com.vaxapp.thingspeak.viewer.domain.NotificationPreferenceRepository
import com.vaxapp.thingspeak.viewer.domain.GetNotificationPreferenceUseCase
import com.vaxapp.thingspeak.viewer.domain.SetNotificationPreferenceUseCase
import com.vaxapp.thingspeak.viewer.view.settings.SettingsViewModel
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

package com.vaxapp.thingspeak.viewer

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.vaxapp.thingspeak.viewer.domain.DomainResponse
import com.vaxapp.thingspeak.viewer.domain.GetNotificationPreferenceUseCase
import com.vaxapp.thingspeak.viewer.domain.GetOfficeWeatherUseCase
import com.vaxapp.thingspeak.viewer.domain.channelDataTooOld
import com.vaxapp.thingspeak.viewer.view.NotificationHelper
import io.reactivex.disposables.Disposable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import kotlin.coroutines.CoroutineContext

class VerifyChannelDataWorkManager(
    private val appContext: Context,
    workerParams: WorkerParameters
) :
    Worker(appContext, workerParams), KoinComponent {

    private var disposable: Disposable? = null

    private val useCase: GetOfficeWeatherUseCase by inject()
    private val getNotificationPreferenceUseCase: GetNotificationPreferenceUseCase by inject()
    private val coroutineContext: CoroutineContext by inject()
    private val notificationHelper: NotificationHelper by inject()

    override fun doWork(): Result {
        CoroutineScope(coroutineContext).launch {
            val displayNotification =
                getNotificationPreferenceUseCase.getNotificationPreference()
            if (displayNotification) {
                disposable = useCase.getOfficeWeather()
                    .subscribe({ result: DomainResponse -> useResult(result) },
                        { error -> error(error) })
            }
        }
        return Result.success()
    }

    private fun useResult(result: DomainResponse) {
        if (result.channelDataTooOld()) {
            notificationHelper.showNotification(appContext)
        }
    }
}

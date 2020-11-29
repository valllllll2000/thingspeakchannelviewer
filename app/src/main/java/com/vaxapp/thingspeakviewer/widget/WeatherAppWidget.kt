/*
 * Copyright (C) 2017 Google Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.vaxapp.thingspeakviewer.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import com.vaxapp.thingspeakviewer.R
import com.vaxapp.thingspeakviewer.domain.DomainResponse
import com.vaxapp.thingspeakviewer.domain.GetOfficeWeatherUseCase
import com.vaxapp.thingspeakviewer.view.main.ViewResponseMapper
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

/**
 * App widget provider class, to handle update broadcast intents and updates
 * for the app widget.
 */
class WeatherAppWidget : AppWidgetProvider(), KoinComponent {

    private val useCase: GetOfficeWeatherUseCase by inject()
    private val mapper: ViewResponseMapper by inject()

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    private fun updateAppWidget(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName, R.layout.new_app_widget)
        views.setTextViewText(R.id.descriptionTv, context.resources.getString(R.string.description))
        views.setTextViewText(R.id.field1Content, "51 %")
        views.setTextViewText(R.id.field2Content, "21 ºC")
        views.setTextViewText(R.id.updatedTv, "Updated: June 15th 2017")

        useCase.getOfficeWeather()
            .subscribe({ result: DomainResponse ->
                display(result,
                    context,
                    appWidgetManager,
                    appWidgetId)
            },
                { error -> error(error) })
    }

    private fun display(
        result: DomainResponse,
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetId: Int
    ) {
        val views = RemoteViews(context.packageName,
            R.layout.new_app_widget)
        val response = mapper.toViewResponse(result)
        views.setTextViewText(R.id.descriptionTv, response.description)
        views.setTextViewText(R.id.field1Content, "${response.field1Value} %")
        views.setTextViewText(R.id.field2Content, "${response.field2Value} ºC")
        views.setTextViewText(R.id.updatedTv,
            context.getString(R.string.last_updated_at, response.formattedDate))

        val intentUpdate = Intent(context, WeatherAppWidget::class.java)
        intentUpdate.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
        val idArray = intArrayOf(appWidgetId)
        intentUpdate.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, idArray)
        val pendingUpdate = PendingIntent.getBroadcast(context,
            appWidgetId, intentUpdate, PendingIntent.FLAG_UPDATE_CURRENT)
        views.setOnClickPendingIntent(R.id.descriptionTv, pendingUpdate)
        appWidgetManager.updateAppWidget(appWidgetId, views)
    }
}

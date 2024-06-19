package com.vaxapp.thingspeak.viewer.view.main

interface MainView {

    fun showLoading()
    fun display(response: ViewResponse)
    fun hideLoading()
    fun showError(error: Throwable)
    fun displayNotification()
}

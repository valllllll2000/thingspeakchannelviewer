package com.vaxapp.thingspeakviewer.view.main

interface MainView {

    fun showLoading()
    fun display(response: ViewResponse)
    fun hideLoading()
    fun showError(error: Throwable)
    fun displayNotification()
}

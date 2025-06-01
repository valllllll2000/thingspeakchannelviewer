package com.vaxapp.thingspeak.viewer.view.main

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.platform.ViewCompositionStrategy
import com.vaxapp.thingspeak.viewer.R
import com.vaxapp.thingspeak.viewer.view.NotificationHelper
import com.vaxapp.thingspeak.viewer.view.ip.IpComposable
import com.vaxapp.thingspeak.viewer.view.ip.IpViewModel
import com.vaxapp.thingspeak.viewer.view.settings.SettingsActivity
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity(), MainView {

    private val presenter: MainPresenter by inject()
    private val notificationHelper by inject<NotificationHelper>()
    private lateinit var descriptionTv: TextView
    private lateinit var humidityTv: TextView
    private lateinit var temperatureTv: TextView
    private lateinit var updatedTv: TextView
    private lateinit var composeView: ComposeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        descriptionTv = findViewById(R.id.descriptionTv)
        humidityTv = findViewById(R.id.field1Content)
        temperatureTv = findViewById(R.id.field2Content)
        updatedTv = findViewById(R.id.updatedTv)
        presenter.view = this
        presenter.onViewReady()
        composeView = findViewById(R.id.compose_view)
        composeView.apply {
            // Dispose of the Composition when the view's LifecycleOwner
            // is destroyed
            setViewCompositionStrategy(ViewCompositionStrategy.DisposeOnViewTreeLifecycleDestroyed)
            setContent {
                // In Compose world
                MaterialTheme {
                    IpComposable(IpViewModel())
                }
            }
        }
    }



    override fun showError(error: Throwable) {
        Toast.makeText(this, getString(R.string.error_loading_toast), Toast.LENGTH_LONG).show()
        error(error)
    }

    override fun displayNotification() {
        Log.i("MainActivity", "ask to display notification")
        notificationHelper.showNotification(this)
    }

    override fun showLoading() {
        // TODO("not implemented")
    }

    override fun hideLoading() {
        // TODO("not implemented")
    }

    @SuppressLint("SetTextI18n")
    override fun display(response: ViewResponse) {
        Log.i("MainActivity", "channel $response")
        descriptionTv.text = response.description
        humidityTv.text = "${response.humidity} %"
        temperatureTv.text = "${response.temperature} ºC"
        updatedTv.text = getString(R.string.last_updated_at, response.channelUpdateDate)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId
        if (id == R.id.settings) {
            startActivity(Intent(this, SettingsActivity::class.java))
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        presenter.dispose()
    }
}

package com.vaxapp.thingspeakviewer

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.vaxapp.thingspeakviewer.data.ApiResponse
import com.vaxapp.thingspeakviewer.data.ApiService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.error
import org.jetbrains.anko.toast
import java.text.SimpleDateFormat
import java.util.*

class MainActivity : AppCompatActivity(), AnkoLogger {

    private val url = "https://api.thingspeak.com/channels/298096/feeds.json?results=1"

    val service by lazy {
        ApiService.create()
    }

    var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        loadData();
    }

    private fun loadData() {
        disposable =
                service.fetchFields(url)
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(
                                { result -> showResult(result) },
                                { error -> showError(error) }
                        )
    }

    private fun showError(error: Throwable) {
        toast("Error loading channel data")
        error(error)
    }

    private fun showResult(response: ApiResponse) {
        info("channel " + response)
        descriptionTv.text = response.channel.description;
        field1Label.text = response.channel.field1
        field2Label.text = response.channel.field2
        field1Content.text = response.feeds[0].field1
        field2Content.text = response.feeds[0].field2
        updatedTv.text = getString(R.string.last_updated_at, getFormattedDate(response))
    }

    private fun getFormattedDate(response: ApiResponse): String? {
        val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'")
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"))
        val date = simpleDateFormat.parse(response.channel.updated_at)
        val localDateFormat = SimpleDateFormat("dd-MM-yyyy HH:mm:ss")
        localDateFormat.timeZone = TimeZone.getDefault()
        return localDateFormat.format(date)
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
        disposable?.dispose()
    }
}

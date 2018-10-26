package com.vaxapp.thingspeakviewer.view.main

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.vaxapp.thingspeakviewer.R
import com.vaxapp.thingspeakviewer.view.SettingsActivity
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.app_bar.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.error
import org.jetbrains.anko.info
import org.jetbrains.anko.toast
import org.koin.android.ext.android.inject


class MainActivity : AppCompatActivity(), AnkoLogger, MainView {

    private val presenter: MainPresenter by inject()

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)
        presenter.view = this
        presenter.onViewReady()
//        loadData()
    }

//    private fun loadData() {
//        disposable =
//                service.fetchFields()
//                        .subscribeOn(Schedulers.io())
//                        .observeOn(AndroidSchedulers.mainThread())
//                        .subscribe(
//                                { result -> showResult(result) },
//                                { error -> showError(error) }
//                        )
//    }

    override fun showError(error: Throwable) {
        toast(getString(R.string.error_loading_toast))
        error(error)
    }

    override fun showLoading() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun hideLoading() {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun display(response: ViewResponse) {
        info("channel $response")
        descriptionTv.text = response.description
        field1Content.text = "${response.field1Value} %"
        field2Content.text = "${response.field2Value} ºC"
        updatedTv.text = getString(R.string.last_updated_at, response.formattedDate)
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

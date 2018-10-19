package cz.fatty.mannheim

import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity;
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.crashlytics.android.Crashlytics
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.crash.FirebaseCrash
import cz.fatty.mannheim.base.BaseActivity
import cz.fatty.mannheim.extensions.observe
import cz.fatty.mannheim.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel

class MainActivity : BaseActivity<MainViewModel>() {

    override fun initViewModel() {
        viewModel = getViewModel()
    }

    override fun getLayoutRes() = R.layout.activity_main

    override fun getContentView() = vContent

    override fun initUi() {
        setupChart()
        Crashlytics.getInstance().crash();
        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            else                 -> super.onOptionsItemSelected(item)
        }
    }

    private fun setupChart() {
        viewModel.bitcoinRates.observe(this) {
            
        }
        var entries = listOf<Entry>(Entry(10F,420F), Entry(12F,420F), Entry(16F,350F))
        val dataSet = LineDataSet(entries, "The Graph")
        val lineData = LineData(dataSet)
        vChart.data = lineData
        vChart.invalidate()
    }
}

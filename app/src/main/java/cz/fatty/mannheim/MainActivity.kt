package cz.fatty.mannheim

import android.view.Menu
import android.view.MenuItem
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import cz.fatty.mannheim.base.BaseActivity
import cz.fatty.mannheim.extensions.observeNonNull
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
        setupDailyChart()
        vSegmentedControl.addOnSegmentClickListener {
            when (it.absolutePosition) {
                PERIOD_DAILY  -> setupDailyChart()
                PERIOD_HOURLY -> setupHourlyChart()
            }
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

    private fun setupDailyChart() {
        viewModel.dailyRates.observeNonNull(this) {
            if (it.isNotEmpty()) {
                val dataSet = LineDataSet(it, "BTC : USD")
                val lineData = LineData(listOf(dataSet))
                vChart.data = lineData

                val xAxis = vChart.xAxis
                xAxis.position = XAxis.XAxisPosition.BOTTOM

                vChart.invalidate()
            }
        }
    }

    private fun setupHourlyChart() {
        viewModel.hourlyRates.observeNonNull(this) {
            if (it.isNotEmpty()) {
                val dataSet = LineDataSet(it, "BTC : USD")
                val lineData = LineData(listOf(dataSet))
                vChart.data = lineData

                val xAxis = vChart.xAxis
                xAxis.position = XAxis.XAxisPosition.BOTTOM

                vChart.invalidate()
            }
        }
    }

    companion object {
        const val PERIOD_DAILY = 0
        const val PERIOD_HOURLY = 1
    }
}

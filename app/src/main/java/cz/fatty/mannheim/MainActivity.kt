package cz.fatty.mannheim

import android.view.Menu
import android.view.MenuItem
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import cz.fatty.mannheim.base.BaseActivity
import cz.fatty.mannheim.extensions.observe
import cz.fatty.mannheim.utils.ChartDateFormatter
import cz.fatty.mannheim.viewModel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*
import org.koin.androidx.viewmodel.ext.android.getViewModel
import java.time.Period

class MainActivity : BaseActivity<MainViewModel>() {

    override fun initViewModel() {
        viewModel = getViewModel()
    }

    override fun getLayoutRes() = R.layout.activity_main

    override fun getContentView() = vContent

    override fun initUi() {
        setupChart()
        vSegmentedControl.addOnSegmentClickListener {
            when (it.absolutePosition) {
                PERIOD_DAILY -> viewModel.periodChanged(PERIOD_DAILY)
                PERIOD_WEEKLY -> viewModel.periodChanged(PERIOD_WEEKLY)
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

    private fun setupChart() {
        viewModel.bitcoinRates.observe(this) {
            if (it != null && it.isNotEmpty()) {
                val dataSet = LineDataSet(it, "BTC : USD")
                val lineData = LineData(listOf(dataSet))
                vChart.data = lineData

                val xAxis = vChart.xAxis
                xAxis.position = XAxis.XAxisPosition.BOTTOM
              //  xAxis.valueFormatter = ChartDateFormatter()

                vChart.invalidate()
            }
        }
    }

    companion object {
        const val PERIOD_DAILY = 0
        const val PERIOD_WEEKLY = 1
    }
}

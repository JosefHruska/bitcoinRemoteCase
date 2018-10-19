package cz.fatty.mannheim.viewModel

import androidx.lifecycle.Transformations
import com.github.mikephil.charting.data.Entry
import cz.fatty.mannheim.base.BaseViewModel
import cz.fatty.mannheim.extensions.lw
import cz.fatty.mannheim.repo.MainRepo
import java.util.*

class MainViewModel(val repo: MainRepo) : BaseViewModel() {

    val bitcoinRates = Transformations.map(repo.getRatesPerHour()) { rates ->
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        rates.filterIndexed { index, x -> index % 60 == 0 }.take(24).map { bitcoinRate ->

            // We have date & time like this: 2018-10-19 16:09:00
            val time = bitcoinRate.time?.split(" ")?.last() // Now we take just time
            val hoursMinutesSeconds = time?.split(":")
            hoursMinutesSeconds?.get(0)?.toInt()?.let {
                calendar.set(Calendar.HOUR_OF_DAY, it)
            }
            hoursMinutesSeconds?.get(1)?.toInt()?.let {
                calendar.set(Calendar.MINUTE, it)
            }
            lw("Original $time -> ${calendar.timeInMillis.toFloat()}")
            Entry(calendar.timeInMillis.toFloat(), bitcoinRate.average ?: 0F)
        }.asReversed()
    }
}
package cz.fatty.mannheim.viewModel

import androidx.lifecycle.Transformations
import com.github.mikephil.charting.data.Entry
import cz.fatty.mannheim.base.BaseViewModel
import cz.fatty.mannheim.repo.MainRepo

class MainViewModel(val repo: MainRepo): BaseViewModel() {

    val bitcoinRates = Transformations.map(repo.getRatesPerHour()) { rates ->
        rates.take(24).mapIndexed{ i, x -> { Entry(x.average ?: 0F, i.toFloat() ) }}
    }

}
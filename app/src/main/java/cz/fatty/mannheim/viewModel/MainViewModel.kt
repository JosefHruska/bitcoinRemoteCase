package cz.fatty.mannheim.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.github.mikephil.charting.data.Entry
import cz.fatty.mannheim.base.BaseViewModel
import cz.fatty.mannheim.repo.MainRepo

class MainViewModel(val repo: MainRepo) : BaseViewModel() {

    val hourlyRates: LiveData<List<Entry>> = Transformations.map(repo.getRatesPerHour()) { rates ->
        rates.mapIndexed { index, rate -> Entry(index.toFloat(), rate.average ?: 0F) }
    }

    val dailyRates: LiveData<List<Entry>> = Transformations.map(repo.getRatesPerDay()) { rates ->
        rates.mapIndexed { index, rate -> Entry(index.toFloat(), rate.average ?: 0F) }
    }
}


package cz.fatty.mannheim.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.github.mikephil.charting.data.Entry
import cz.fatty.mannheim.objects.BitcoinRate

interface MainRepo {

    fun getRatesPerHour(): LiveData<List<BitcoinRate>>
}
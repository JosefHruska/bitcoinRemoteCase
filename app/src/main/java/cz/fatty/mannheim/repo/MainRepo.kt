package cz.fatty.mannheim.repo

import androidx.lifecycle.LiveData
import cz.fatty.mannheim.objects.BitcoinDailyRate
import cz.fatty.mannheim.objects.BitcoinRate

interface MainRepo {

    fun getRatesPerHour(): LiveData<List<BitcoinRate>>

    fun getRatesPerDay(): LiveData<List<BitcoinDailyRate>>

}
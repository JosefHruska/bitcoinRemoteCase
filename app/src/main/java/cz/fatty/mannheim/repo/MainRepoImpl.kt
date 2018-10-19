package cz.fatty.mannheim.repo

import androidx.lifecycle.LiveData
import cz.fatty.mannheim.api.MainApiHandler
import cz.fatty.mannheim.extensions.subscribeOnBackground
import cz.fatty.mannheim.networking.CallbackWrapper
import cz.fatty.mannheim.objects.BitcoinDailyRate
import cz.fatty.mannheim.objects.BitcoinRate

class MainRepoImpl(private val bitcoinDao: BitcoinDao, private val api: MainApiHandler) : MainRepo {

    override fun getRatesPerDay(): LiveData<List<BitcoinDailyRate>> {
        api.getDailyRates().subscribeOnBackground(object : CallbackWrapper<List<BitcoinDailyRate>>() {
            override fun onResponse(response: ApiData<List<BitcoinDailyRate>>) {
                response.data?.let {
                    bitcoinDao.nukeDailyRates()
                    bitcoinDao.saveDailyRates(it.take(30))
                }
            }
        })
        return bitcoinDao.getDailyRates()
    }

    override fun getRatesPerHour(): LiveData<List<BitcoinRate>> {
        api.getHourlyRates().subscribeOnBackground(object : CallbackWrapper<List<BitcoinRate>>() {
            override fun onResponse(response: ApiData<List<BitcoinRate>>) {
                response.data?.let {
                    bitcoinDao.nukeRates()
                    bitcoinDao.saveHourlyRates(it.take(24))
                }
            }
        })
        return bitcoinDao.getHourlyRates()
    }
}
package cz.fatty.mannheim.repo

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import cz.fatty.mannheim.Db
import cz.fatty.mannheim.api.MainApiHandler
import cz.fatty.mannheim.extensions.subscribeOnBackground
import cz.fatty.mannheim.networking.CallbackWrapper
import cz.fatty.mannheim.objects.BitcoinRate

class MainRepoImpl(private val bitcoinDao: BitcoinDao, private val api: MainApiHandler) : MainRepo {

    override fun getRatesPerHour(): LiveData<List<BitcoinRate>> {
        api.getRates().subscribeOnBackground(object : CallbackWrapper<List<BitcoinRate>>() {
            override fun onResponse(response: ApiData<List<BitcoinRate>>) {
                response.data?.let {
                    bitcoinDao.saveRates(it)
                }
            }
        })
        return bitcoinDao.getRates()
    }
}
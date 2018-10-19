package cz.fatty.mannheim.api

import cz.fatty.mannheim.base.BaseApiService
import cz.fatty.mannheim.objects.BitcoinDailyRate
import cz.fatty.mannheim.objects.BitcoinRate
import io.reactivex.Observable

class MainApiHandler(var apiService: MainApi) : BaseApiService() {

    fun getHourlyRates(): Observable<List<BitcoinRate>> = apiService.getHourlyRates()

    fun getDailyRates(): Observable<List<BitcoinDailyRate>> = apiService.getDailyRates()

}
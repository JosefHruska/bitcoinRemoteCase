package cz.fatty.mannheim.api

import cz.fatty.mannheim.base.BaseApiService
import cz.fatty.mannheim.objects.BitcoinRate
import io.reactivex.Observable

class MainApiHandler(var apiService: MainApi) : BaseApiService() {

    fun getDailyRates(): Observable<List<BitcoinRate>>  = apiService.getMonthlyRates()

    fun getMonthlyRates(): Observable<List<BitcoinRate>>  = apiService.getMonthlyRates()

}
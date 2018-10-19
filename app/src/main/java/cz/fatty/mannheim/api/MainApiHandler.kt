package cz.fatty.mannheim.api

import cz.fatty.mannheim.Db
import cz.fatty.mannheim.base.BaseApiService
import cz.fatty.mannheim.objects.BitcoinRate
import io.reactivex.Observable

class MainApiHandler(var apiService: MainApi) : BaseApiService() {

    fun getRates(): Observable<List<BitcoinRate>>  = apiService.getRates()

}
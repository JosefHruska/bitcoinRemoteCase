package cz.fatty.mannheim.api

import cz.fatty.mannheim.objects.BitcoinDailyRate
import cz.fatty.mannheim.objects.BitcoinRate
import io.reactivex.Observable
import retrofit2.http.GET

interface MainApi {

    @GET("https://apiv2.bitcoinaverage.com/indices/global/history/BTCUSD?period=monthly&format=json")
    fun getHourlyRates(): Observable<List<BitcoinRate>>

    @GET("https://apiv2.bitcoinaverage.com/indices/global/history/BTCUSD?period=alltime&format=json")
    fun getDailyRates(): Observable<List<BitcoinDailyRate>>
}
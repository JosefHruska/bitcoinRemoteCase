package cz.fatty.dreamer.api

import cz.fatty.dreamer.objects.BitcoinAverage
import io.reactivex.Observable
import retrofit2.http.GET

interface BaseApi {

    @GET("https://apiv2.bitcoinaverage.com/indices/global/history/BTCUSD?period=daily&format=json")
    fun getAverages() : Observable<BitcoinAverage>
}
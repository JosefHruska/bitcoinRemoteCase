package cz.fatty.mannheim.api

import cz.fatty.mannheim.objects.BitcoinRate
import io.reactivex.Observable
import retrofit2.http.GET

interface MainApi {

    @GET("https://apiv2.bitcoinaverage.com/indices/global/history/BTCUSD?period=daily&format=json")
    fun getRates() : Observable<BitcoinRate>
}
package cz.fatty.mannheim.api

import cz.fatty.mannheim.Db
import cz.fatty.mannheim.base.BaseApiService
import io.reactivex.Observable

class MainApiHandler(var apiService: MainApi, var database: Db) : BaseApiService(database) {

    fun getRates() = apiService.getRates()

}
package cz.fatty.mannheim.networking

import android.util.Log
import cz.fatty.mannheim.repo.ApiData
import io.reactivex.observers.DisposableObserver
import retrofit2.HttpException
import java.io.IOException
import java.net.SocketTimeoutException

open class CallbackWrapper<T> : DisposableObserver<T>() {

    val data: ApiData<T> = ApiData()

    override fun onNext(response: T) {
        data.state = ApiData.State.OK
        data.data = response
        onResponse(data)
    }

    override fun onError(throwable: Throwable) {
        when (throwable) {
            is NoNetworkException     -> {
              /*  Crashlytics.log(
                    Log.ERROR, BaseApiService.TAG, "No connection available (${throwable.message})"
                )*/
                data.state = ApiData.State.NET_NO_NETWORK
            }
            is HttpException          -> {
                when (throwable.response().code()) {
                    ApiData.State.BAD_REQUEST.code      -> data.state = ApiData.State.BAD_REQUEST
                    ApiData.State.UNAUTHORIZED.code     -> data.state = ApiData.State.UNAUTHORIZED
                    ApiData.State.FORBIDDEN.code        -> data.state = ApiData.State.FORBIDDEN
                    ApiData.State.NOT_FOUND.code        -> data.state = ApiData.State.NOT_FOUND
                    ApiData.State.ENTITY_TOO_LARGE.code -> data.state =
                            ApiData.State.ENTITY_TOO_LARGE
                    ApiData.State.SERVER_ERROR.code     -> data.state = ApiData.State.SERVER_ERROR
                    else                                -> data.state = ApiData.State.OTHER
                }
               /* Crashlytics.log(
                    Log.ERROR,
                    BaseApiService.TAG,
                    "HTTP Error, code ${data.state}(${throwable.response().code()}) > 300. (${throwable.response().raw().request().url()})"
                )*/
            }
            is SocketTimeoutException -> {
             //   Crashlytics.log(Log.ERROR, BaseApiService.TAG, "Timeout exception")
                data.state = ApiData.State.NET_TIMEOUT
            }
            is IOException            -> {
               /* Crashlytics.log(
                    Log.ERROR, BaseApiService.TAG, "IO Exception (${throwable.message})"
                )*/
                data.state = ApiData.State.NET_IO_ERROR
            }
            else                      -> {
                //Crashlytics.log(Log.ERROR, BaseApiService.TAG, "Other: ${throwable.message}")
                data.state = ApiData.State.NET_OTHER_ERROR
            }
        }
      //  Crashlytics.logException(throwable)
        data.data = null
        onResponse(data)
    }

    override fun onComplete() {}

    open fun onResponse(response: ApiData<T>) {}
}
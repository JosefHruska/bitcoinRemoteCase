package cz.fatty.mannheim.base

import android.util.Log
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import cz.fatty.mannheim.R
import cz.fatty.mannheim.extensions.toText
import cz.fatty.mannheim.networking.NetworkMonitor
import cz.fatty.mannheim.networking.NoNetworkException
import cz.fatty.mannheim.repo.ApiData
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.IOException

open class BaseApiService() {

    inline fun <reified T> getRetro(baseUrl: String) = createWebService<T>(baseUrl)

    inline fun <reified T> createWebService(baseUrl: String): T {
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .client(getClient())
            .build()
        return retrofit.create(T::class.java)
    }

    fun getClient(): OkHttpClient {
        val bodyLoggin = HttpLoggingInterceptor()
        bodyLoggin.level = HttpLoggingInterceptor.Level.BODY

        val networkMonitor = NetworkMonitor

        val errorLoggin = Interceptor { chain ->
            val request = chain.request()
            val response: Response?
            try {
                response = chain.proceed(request)
            } catch (e: IOException) {
                throw IOException("${request.url().url()}")
            }

            Log.w(TAG, "Response code is for ${request.url()} is: ${response.code()}")
            if (response.code() == ApiData.State.SERVER_ERROR.code) {
                return@Interceptor response
            } else if (response.code() == ApiData.State.UNAUTHORIZED.code) {
                // TODO: Handle state here
            }
            response
        }

        return OkHttpClient.Builder()
            .addInterceptor { chain ->
                val newRequestBuilder = chain.request().newBuilder()
                val newRequest = newRequestBuilder.build()
                chain.proceed(newRequest)
            }
            .addInterceptor { chain ->
                if (!networkMonitor.isConnected()) {
                    throw NoNetworkException("${chain.request().url().url()}")
                } else {
                    chain.proceed(chain.request())
                }
            }
            .addInterceptor(bodyLoggin)
            .addInterceptor(errorLoggin)
            .build()
    }

    companion object {

        val TAG = "networking"

        private val URL = R.string.api_url.toText()

        fun getUrl() = URL
    }
}
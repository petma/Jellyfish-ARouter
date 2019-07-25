package com.logic.jellyfish.http

import android.content.Context
import android.net.ConnectivityManager
import com.logic.jellyfish.BuildConfig
import com.logic.jellyfish.app.App
import okhttp3.*
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.util.concurrent.TimeUnit

object RetrofitFactory {

    val aMapService: AMapService by lazy { createAMapService() }

    private const val HOSPITAL_URL = "http://39.108.79.16"
    private const val A_MAP_URL = "https://tsapi.amap.com"

    private fun createAMapService(): AMapService {
        return createRetrofit(A_MAP_URL).create(AMapService::class.java)
    }

    private fun createRetrofit(url: String): Retrofit {
        return Retrofit.Builder()
            .client(initOkHttpClient())
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    private fun initOkHttpClient(): OkHttpClient {
        // 缓存地址
        val cacheDir = File(App.app.cacheDir, "responses")
        // 500m 缓存
        val cache = Cache(cacheDir, 500 * 1024 * 1024)
        // Create an ssl socket factory radius8 our all-trusting manager
        val okBuilder = OkHttpClient.Builder()
        okBuilder // 添加缓存，无网访问时会拿缓存,只会缓存get请求
            .addInterceptor(AddCacheInterceptor())
            .cache(cache)
            .addInterceptor(
                HttpLoggingInterceptor().setLevel(
                    if (BuildConfig.DEBUG)
                        HttpLoggingInterceptor.Level.BODY
                    else
                        HttpLoggingInterceptor.Level.NONE
                )
            )
        return okBuilder.build()
    }

    private class AddCacheInterceptor : Interceptor {

        override fun intercept(chain: Interceptor.Chain): Response {

            val cacheControl = CacheControl.Builder()
                .maxAge(0, TimeUnit.SECONDS)
                .maxStale(365, TimeUnit.DAYS)
                .maxAge(0, TimeUnit.SECONDS)
                .maxStale(365, TimeUnit.DAYS)
                .build()

            var request = chain.request()
            if (!isNetworkConnected()) {
                request = request.newBuilder()
                    .cacheControl(cacheControl)
                    .build()
            }
            val originalResponse = chain.proceed(request)

            return if (isNetworkConnected()) {
                // read from cache
                val maxAge = 0
                originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public ,max-age=$maxAge")
                    .build()
            } else {
                // tolerate 4-weeks stale
                val maxStale = 60 * 60 * 24 * 28
                originalResponse.newBuilder()
                    .removeHeader("Pragma")
                    .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                    .build()
            }
        }
    }

    fun isNetworkConnected(): Boolean {
        return try {
            val cm = App.app.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val info = cm.activeNetworkInfo
            info != null && info.isConnected
        } catch (e: Exception) {
            e.printStackTrace()
            false
        }
    }
}
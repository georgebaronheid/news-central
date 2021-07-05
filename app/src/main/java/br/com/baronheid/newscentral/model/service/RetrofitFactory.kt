package br.com.baronheid.newscentral.model.service

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitFactory {

    private val retrofitFactory = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun newsService() : NewsService = retrofitFactory
            .create(NewsService::class.java)

    companion object {
        const val BASE_URL = "https://newsapi.org/v2/"
    }
}

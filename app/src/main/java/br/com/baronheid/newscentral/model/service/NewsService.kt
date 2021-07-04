package br.com.baronheid.newscentral.model.service

import br.com.baronheid.newscentral.model.entities.Countries
import br.com.baronheid.newscentral.model.entities.News
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

interface NewsService {

    @Headers("X-Api-Key: 0574dde348e64b6db498fec89a084fa7")
    @GET("top-headlines")
    fun getTopHeadlines(
        @Query("country") countries: String? = "br",
        @Query("size") size: Int? = 10,
        @Query("page") page: Int? = 1
    ): Call<News>

    @GET("")
}

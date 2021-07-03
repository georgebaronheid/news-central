package br.com.baronheid.newscentral.model.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class News(
    @SerializedName("status") val status: String,
    @SerializedName("totalResults") val totalResults: Int,
    @SerializedName("articles") val articles: List<Article?> = emptyList<Article>()
) : Serializable

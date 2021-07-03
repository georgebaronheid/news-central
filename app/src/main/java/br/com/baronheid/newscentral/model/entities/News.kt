package br.com.baronheid.newscentral.model.entities

data class News(
    val status: String,
    val totalResults: Int,
    val articles: List<Article?> = emptyList<Article>()
)

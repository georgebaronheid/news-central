package br.com.baronheid.newscentral

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.baronheid.newscentral.databinding.ActivityMainBinding
import br.com.baronheid.newscentral.model.entities.Article
import br.com.baronheid.newscentral.model.service.NewsService
import br.com.baronheid.newscentral.model.service.RetrofitFactory
import br.com.baronheid.newscentral.views.DetailActivity
import br.com.baronheid.newscentral.views.NewsAdapter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse


class MainActivity : AppCompatActivity() {
    private var displayedArticles = mutableListOf<Article>()
    private var adapterView = NewsAdapter(this::onArticleItemClick)
    private lateinit var binding: ActivityMainBinding

    private fun onArticleItemClick(article: Article) {
        Intent(this@MainActivity, DetailActivity::class.java)
            .apply {
                putExtra("clickedArticle", article)
                startActivity(this)
            }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initRecyclerView()

        val call = RetrofitFactory().newsService()

        onSwipeAction(call)

        getNewsCoroutine(call)
    }

    private fun initRecyclerView() {
        binding.mainRecycler.adapter = adapterView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        binding.mainRecycler.layoutManager = layoutManager
    }

    override fun onRetainCustomNonConfigurationInstance(): Any {
        return displayedArticles
    }

    private fun onSwipeAction(call: NewsService) {
        binding.swipeRefreshMain.setOnRefreshListener { getNewsCoroutine(call) }

    }

    private fun getNewsCoroutine(call: NewsService) {
        GlobalScope.launch(Dispatchers.IO) {
            call
                .getTopHeadlines()
                .awaitResponse()
                .takeIf { it.isSuccessful && !it.body()!!.articles.isNullOrEmpty() }
                .apply {
                    this@MainActivity.runOnUiThread {
                        displayedArticles = this!!.body()!!.articles as MutableList<Article>
                        adapterView.setData(displayedArticles)
                        binding.swipeRefreshMain.isRefreshing = false
                    }
                }
        }
    }
}



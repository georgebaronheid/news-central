package br.com.baronheid.newscentral

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import br.com.baronheid.newscentral.databinding.ActivityMainBinding
import br.com.baronheid.newscentral.model.entities.Article
import br.com.baronheid.newscentral.model.service.NewsService
import br.com.baronheid.newscentral.model.service.RetrofitFactory
import br.com.baronheid.newscentral.views.NewsAdapter
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.awaitResponse

private const val LOG_TAG = "MAIN_ACTIVITY"

class MainActivity : AppCompatActivity() {
    private var displayedArticles = mutableListOf<Article>()
    private var adapterView = NewsAdapter(displayedArticles, this::onArticleItemClick)

    private lateinit var binding: ActivityMainBinding

    private fun onArticleItemClick(article: Article): Unit = TODO()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initRecyclerView()

        val call = RetrofitFactory().newsService()

//        binding.mainNewsCard.imageDownloadButton.setOnClickListener {
//            downloadImage()
//        }

        onSwipeAction(call)

        getNewsCoroutine(call)
    }

    private fun initRecyclerView() {
        main_recycler.adapter = adapterView
        val layoutManager = LinearLayoutManager(this)
        layoutManager.orientation = RecyclerView.VERTICAL
        main_recycler.layoutManager = layoutManager
    }
//
//    private fun downloadImage() {
//        val hasPermission = checkStoragePermission(
//            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
//            RC_STORAGE_PERMISSION
//        )
//        if (!hasPermission) return
//        val generator = Random().nextInt(1000)
//        val fileName = "image-$generator.png"
//        val bitmap = binding.mainNewsCard.cardsImage.drawable.toBitmap()
//        MediaStore.Images.Media.insertImage(contentResolver, bitmap, fileName, "NewsCentral image");
//    }
//
//    private fun checkStoragePermission(permission: String, requestCode: Int): Boolean {
//        if (ActivityCompat.checkSelfPermission(
//                this,
//                permission
//            ) != PackageManager.PERMISSION_GRANTED
//        ) {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
//                Toast.makeText(
//                    this,
//                    "Este aplicativo necessita de permissão para salvar imagens",
//                    Toast.LENGTH_SHORT
//                ).show()
//            }
//            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
//            return false
//        }
//        return true
//    }
//
//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<out String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        when (requestCode) {
//            RC_STORAGE_PERMISSION -> {
//                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) Toast.makeText(
//                    this,
//                    "Permission granted",
//                    Toast.LENGTH_SHORT
//                ).show()
//                else Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
//            }
//        }
//    }

    private fun onSwipeAction(call: NewsService) {
        binding.swipeRefreshMain.setOnRefreshListener { getNewsCoroutine(call) }
    }

    private fun getNewsCoroutine(call: NewsService) {
        GlobalScope.launch(Dispatchers.IO) {
            call
                .getTopHeadlines()
                .awaitResponse()
                .body()
                ?.articles.let {
                    val localMutable = mutableListOf<Article>()
                    if (!it.isNullOrEmpty()) {
                        TODO("Como adicionar o resultado na recyclerview?")
                        it.toCollection(localMutable)
                    }
                }


        }
    }

//    private fun applyUiChanges(article: Article) {
//        binding.mainNewsCard.cardsImage.load(article.urlToImage)
//        binding.mainNewsCard.cardHeader.text = article.title
//        binding.mainNewsCard.cardSubtitle.text = article.title
//        binding.swipeRefreshMain.isRefreshing = false
//    }

//    companion object {
//        const val RC_STORAGE_PERMISSION = 0
//    }
}



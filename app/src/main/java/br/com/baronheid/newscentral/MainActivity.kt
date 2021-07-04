package br.com.baronheid.newscentral

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.baronheid.newscentral.databinding.ActivityMainBinding
import br.com.baronheid.newscentral.model.entities.News
import br.com.baronheid.newscentral.model.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.RuntimeException
import java.net.URL

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val call = RetrofitFactory().newsService().getTopHeadlines()

        call
            .enqueue(object : Callback<News> {

                override fun onResponse(call: Call<News>, response: Response<News>) {

                    response.body()?.let {
                        Log.i("IMG_URL", "${it.articles[0]?.urlToImage}")

                        binding.mainNewsCard.cardHeader.text = it.articles[0]?.title ?: "none"
                        binding.mainNewsCard.cardSubtitle.text = it.articles[0]?.description
                        binding.mainNewsCard.cardsImage
                    } ?: Toast.makeText(
                        this@MainActivity,
                        "No body",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }


                override fun onFailure(call: Call<News>, t: Throwable) {
                    Log.e("ERROR: ", t.message!!)
                }

            })

    }




}



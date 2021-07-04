package br.com.baronheid.newscentral

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.Contacts
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.com.baronheid.newscentral.databinding.ActivityMainBinding
import br.com.baronheid.newscentral.model.entities.News
import br.com.baronheid.newscentral.model.service.RetrofitFactory
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.await
import java.lang.RuntimeException
import java.net.URL

class MainActivity : AppCompatActivity() {


    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val call = RetrofitFactory().newsService()

        val cardContent = object {}

        GlobalScope.launch(Dispatchers.IO) {
            val news = call
                .getTopHeadlines()
                .await()
                .articles[0]
                .also {
                    this@MainActivity.runOnUiThread {
                            val bitmap = BitmapFactory.decodeStream(URL(it!!.url).openConnection().getInputStream())
                            binding.mainNewsCard.cardsImage.setImageBitmap(bitmap)
                            binding.mainNewsCard.cardHeader.text = it.title
                            binding.mainNewsCard.cardHeader.text = it.title
                    }
                }

            launch {

            }
        }




    }




}



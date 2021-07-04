package br.com.baronheid.newscentral

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import br.com.baronheid.newscentral.databinding.ActivityMainBinding
import br.com.baronheid.newscentral.model.entities.News
import br.com.baronheid.newscentral.model.service.RetrofitFactory
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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
                        Log.i("BODY", "${it.totalResults}")
                        Toast.makeText(
                            this@MainActivity,
                            "${response.body()?.totalResults}",
                            Toast.LENGTH_LONG
                        )
                            .show()
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

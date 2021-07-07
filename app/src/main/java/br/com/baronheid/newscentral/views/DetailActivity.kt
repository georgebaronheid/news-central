package br.com.baronheid.newscentral.views

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.graphics.drawable.toBitmap
import br.com.baronheid.newscentral.R
import br.com.baronheid.newscentral.databinding.ActivityDetailBinding
import br.com.baronheid.newscentral.model.entities.Article
import coil.load
import java.util.*

class DetailActivity : AppCompatActivity() {
    private lateinit var article: Article

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setUpButtons()
        getContent()
    }

    private fun setUpButtons() {
        binding.topAppBar.setNavigationOnClickListener {
            finish()
        }
        binding.topAppBar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.download -> downloadImage().run { true }
                R.id.share -> shareUrl().run { true }
                else -> false
            }
        }
    }

    private fun getContent() {
        article = intent.getSerializableExtra("clickedArticle") as Article
        binding.detailImage.load(article.urlToImage)
        binding.detailHeader.text = article.title
        binding.detailSubtitle.text = article.description
        binding.detailCompleteText.text = article.content
    }

    private fun shareUrl() {
        val shareIntent = Intent()
            .setAction(Intent.ACTION_SEND)
            .apply {
                putExtra(Intent.EXTRA_TEXT, article.url)
                type = "text/plain"
            }
        startActivity(shareIntent)
    }


    private fun downloadImage() {
        val hasPermission = checkStoragePermission(
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE,
            RC_STORAGE_PERMISSION
        )
        if (!hasPermission) return
        val generator = Random().nextInt(1000)
        val fileName = "image-$generator.png"
        val bitmap = binding.detailImage.drawable.toBitmap()
        MediaStore.Images.Media.insertImage(contentResolver, bitmap, fileName, getString(R.string.saved_image_description))
        Toast.makeText(this, getString(R.string.image_downloaded), Toast.LENGTH_LONG).show()
    }

    private fun checkStoragePermission(permission: String, requestCode: Int): Boolean {
        if (ActivityCompat.checkSelfPermission(
                this,
                permission
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(this, permission)) {
                Toast.makeText(
                    this,
                    getString(R.string.need_permission),
                    Toast.LENGTH_SHORT
                ).show()
            }
            ActivityCompat.requestPermissions(this, arrayOf(permission), requestCode)
            return false
        }
        return true
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            RC_STORAGE_PERMISSION -> {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) Toast.makeText(
                    this,
                    getString(R.string.permission_granted),
                    Toast.LENGTH_SHORT
                )
                    .show()
                    .also { downloadImage() }
                else Toast.makeText(this, getString(R.string.permission_denied), Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {
        const val RC_STORAGE_PERMISSION = 0
    }
}

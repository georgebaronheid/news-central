package br.com.baronheid.newscentral.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.baronheid.newscentral.R
import br.com.baronheid.newscentral.model.entities.Article
import br.com.baronheid.newscentral.model.entities.News
import coil.load
import kotlinx.android.synthetic.main.news_cards.view.*
import okhttp3.internal.notify
import retrofit2.Response

class NewsAdapter(
    private val callback: (Article) -> Unit
) : RecyclerView.Adapter<NewsAdapter.VH>() {

    private val articles: MutableList<Article> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.news_cards, parent, false)
        return VH(view)
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        holder.itemView.setOnClickListener {
            TODO("Aqui preciso pegar o objeto ARticle clicando pra passar pra nova activity")
        }
        val urlToImage = articles[position].urlToImage
        val title = articles[position].title
        val description = articles[position].description
        holder.imageView.load(urlToImage)
        holder.header.text = title
        holder.subtitle.text = description
    }

    override fun getItemCount(): Int = articles.size

    fun setData(articles: List<Article>) {
        this.articles.clear()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.cards_image
        val header: TextView = itemView.card_header
        val subtitle: TextView = itemView.card_subtitle
    }

}


package br.com.baronheid.newscentral.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import br.com.baronheid.newscentral.R
import br.com.baronheid.newscentral.model.entities.Article
import coil.load

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

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(articles[position])


    override fun getItemCount(): Int = articles.size

    fun setData(articles: List<Article>) {
        this.articles.clear()
        this.articles.addAll(articles)
        notifyDataSetChanged()
    }

    inner class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val imageView: ImageView = itemView.findViewById(R.id.cards_image)
        private val header: TextView = itemView.findViewById(R.id.card_header)
        private val subtitle: TextView = itemView.findViewById(R.id.card_subtitle)

        fun bind(article: Article) {
            imageView.load(article.urlToImage)
            header.text = article.title
            subtitle.text = article.description
            itemView.rootView.setOnClickListener {
                callback(article)
            }
        }
    }

}


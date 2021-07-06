package br.com.baronheid.newscentral.views

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import br.com.baronheid.newscentral.R
import br.com.baronheid.newscentral.model.entities.Article
import coil.load
import kotlinx.android.synthetic.main.news_cards.view.*

class NewsAdapter(
    private val articles: List<Article>,
    private val callback: (Article) -> Unit
) : RecyclerView.Adapter<NewsAdapter.VH>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val view = LayoutInflater
            .from(parent.context)
            .inflate(R.layout.news_cards, parent, false)
        val viewHolder = VH(view)
        return viewHolder
    }

    override fun onBindViewHolder(holder: VH, position: Int) {
        val urlToImage = articles[position].urlToImage
        val title = articles[position].title
        val description = articles[position].description
        holder.imageView.load(urlToImage)
        holder.header.text = title
        holder.subtitle.text = description
    }

    override fun getItemCount(): Int = articles.size

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.cards_image
        val header: TextView = itemView.card_header
        val subtitle: TextView = itemView.card_subtitle
    }

}


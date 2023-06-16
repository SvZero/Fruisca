package com.glendito.fruisca.ui.home.news

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.glendito.fruisca.database.entities.NewsEntity
import com.glendito.fruisca.databinding.ItemNewsBinding

class NewsAdapter(
    val onClickDetail: (Int) -> Unit
) : RecyclerView.Adapter<NewsAdapter.ViewHolder>() {

    private val news = arrayListOf<NewsEntity>()
    private lateinit var binding: ItemNewsBinding

    fun setNews(news: List<NewsEntity>) {
        this.news.clear()
        this.news.addAll(news)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.ViewHolder {
        binding = ItemNewsBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return ViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
        holder.bindItem(news[position])
    }

    override fun getItemCount(): Int = news.size

    inner class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        fun bindItem(newsEntity: NewsEntity) {
            Glide.with(itemView.context)
                .load(newsEntity.image)
                .into(binding.img)

            binding.txtContent.text = if (newsEntity.content.length > 100) {
                "${newsEntity.content.subSequence(0, 100)}..."
            } else {
                newsEntity.content.subSequence(0, 100)
            }
            binding.txtTitle.text = newsEntity.title
            binding.root.setOnClickListener {
                onClickDetail(newsEntity.id)
            }
        }
    }
}
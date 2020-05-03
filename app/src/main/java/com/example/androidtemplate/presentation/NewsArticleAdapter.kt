package com.example.androidtemplate.presentation

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import kotlinx.android.synthetic.main.item_news_article_list.view.*

/**
 * Created by Yossi Segev on 14/11/2017.
 */
class NewsArticleAdapter : RecyclerView.Adapter<NewsArticleViewHolder>() {

    private var data: List<ArticleDomainEntity> = mutableListOf()

    fun update(data: List<ArticleDomainEntity>) {
        this.data = data
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsArticleViewHolder {
        return NewsArticleViewHolder.create(parent)
    }

    override fun onBindViewHolder(holder: NewsArticleViewHolder, position: Int) {
        val item = data[position]
        holder.bind(item)
        holder.itemView.tvNumber.text = position.toString()
    }
}
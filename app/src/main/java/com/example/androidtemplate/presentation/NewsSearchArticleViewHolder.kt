package com.example.androidtemplate.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.androidtemplate.R
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import kotlinx.android.synthetic.main.item_news_article_list.view.*

class NewsSearchArticleViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup): NewsSearchArticleViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.item_news_article_list,
                parent,
                false
            )
            return NewsSearchArticleViewHolder(view)
        }
    }

    fun bind(item: ArticleDomainEntity) {
        itemView.run {
            Glide.with(context).load(item.urlToImage).into(iv)
            tvAuthor.text = item.author
            tvTitle.text = item.title
        }
    }
}
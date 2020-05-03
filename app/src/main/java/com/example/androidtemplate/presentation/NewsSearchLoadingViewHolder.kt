package com.example.androidtemplate.presentation

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtemplate.R
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.helper.LoadingMoreState
import kotlinx.android.synthetic.main.item_loading_more.view.*

class NewsSearchLoadingViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    companion object {
        fun create(parent: ViewGroup): NewsSearchLoadingViewHolder {
            val view = LayoutInflater.from(parent?.context).inflate(
                R.layout.item_loading_more,
                parent,
                false
            )
            return NewsSearchLoadingViewHolder(view)
        }
    }

    fun bind(state: LoadingMoreState) {
        itemView.run {
            when (state) {
                is LoadingMoreState.Loading -> {
                    pb.visibility = View.VISIBLE
                    tvError.visibility = View.INVISIBLE
                    tvDone.visibility = View.INVISIBLE
                }

                is LoadingMoreState.Error -> {
                    pb.visibility = View.INVISIBLE
                    tvError.visibility = View.VISIBLE
                    tvDone.visibility = View.INVISIBLE
                }

                is LoadingMoreState.Done -> {
                    pb.visibility = View.INVISIBLE
                    tvError.visibility = View.INVISIBLE
                    tvDone.visibility = View.VISIBLE
                }
            }
        }
    }
}
package com.example.androidtemplate.presentation

import android.os.Handler
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.helper.LoadingMoreState
import kotlinx.android.synthetic.main.item_loading_more.view.*

/**
 * Created by Yossi Segev on 14/11/2017.
 */
class NewsSearchArticleAdapter : PagedListAdapter<ArticleDomainEntity, RecyclerView.ViewHolder>(
    diffCallback
) {

    private var loadingMoreState: LoadingMoreState = LoadingMoreState.Loading()
    lateinit var onClickItemListener: OnClickItemListener<ArticleDomainEntity>

    override fun getItemViewType(position: Int): Int {
        return if (position < super.getItemCount()) DATA_VIEW_TYPE else FOOTER_VIEW_TYPE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == DATA_VIEW_TYPE) NewsArticleViewHolder.create(parent) else NewsSearchLoadingViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            DATA_VIEW_TYPE -> {
                getItem(position)?.let { item ->
                    (holder as NewsArticleViewHolder).bind(item)
                    holder.itemView.setOnClickListener { onClickItemListener.onClickItem(item) }
                }
            }
            else -> {
                (holder as NewsSearchLoadingViewHolder).bind(loadingMoreState)
                holder.itemView.tvError.setOnClickListener { onClickItemListener.onRetry() }
            }
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount() + if (hasFooter()) 1 else 0
    }

    private fun hasFooter(): Boolean {
        return super.getItemCount() != 0 && loadingMoreState != null
    }

    fun setLoading(state: LoadingMoreState) {
        Handler().post {
            this.loadingMoreState = state
            notifyDataSetChanged()
        }
    }

    companion object {
        const val DATA_VIEW_TYPE = 1
        const val FOOTER_VIEW_TYPE = 2

        val diffCallback = object : DiffUtil.ItemCallback<ArticleDomainEntity>() {
            override fun areItemsTheSame(
                oldItem: ArticleDomainEntity,
                newItem: ArticleDomainEntity
            ): Boolean {
                return oldItem.url == newItem.url
            }

            override fun areContentsTheSame(
                oldItem: ArticleDomainEntity,
                newItem: ArticleDomainEntity
            ): Boolean {
                return oldItem == newItem
            }
        }
    }
}
package com.example.androidtemplate.presentation

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtemplate.R
import com.example.androidtemplate.data.*
import com.example.androidtemplate.data.local.NewsDatabase
import com.example.androidtemplate.data.local.NewsLocalDataSource
import com.example.androidtemplate.data.remote.ApiClient
import com.example.androidtemplate.data.remote.ApiInterface
import com.example.androidtemplate.data.remote.NewsRemoteDataSource
import com.example.androidtemplate.domain.entity.ArticleDomainEntity
import com.example.androidtemplate.domain.usecase.SearchArticlesPaginationByQuery
import kotlinx.android.synthetic.main.fragment_search_news_article_list.*
import timber.log.Timber

/**
 * Created by Yossi Segev on 11/11/2017.
 */
class NewsSearchArticleFragment : Fragment() {
    private lateinit var factory: NewsSearchArticleViewModelFactory
    private lateinit var viewModel: NewsSearchArticleViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var newsArticleAdapter: NewsSearchArticleAdapter
    private lateinit var edtSearch: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_search_news_article_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        newsArticleAdapter = NewsSearchArticleAdapter()
        recyclerView = rv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = newsArticleAdapter
        edtSearch = edt


        val client = ApiClient.instance(context!!)
        val api = client.create(ApiInterface::class.java)
        val articleMapper = ArticleMapper()
        val sourceMapper = SourceMapper()
        val remoteDataSource = NewsRemoteDataSource(api, articleMapper, sourceMapper)

        val db = NewsDatabase.getInstance(Any())
        val localDataSource = NewsLocalDataSource(db, articleMapper, sourceMapper)
        val repository = NewsRepository(remoteDataSource, localDataSource)
        val searchUseCase = SearchArticlesPaginationByQuery(repository)

        factory = NewsSearchArticleViewModelFactory(searchUseCase)
        viewModel = ViewModelProvider(this, factory).get(NewsSearchArticleViewModel::class.java)

        newsArticleAdapter.onClickItemListener = object : OnClickItemListener<ArticleDomainEntity> {
            override fun onClickItem(item: ArticleDomainEntity) {
                Timber.e("showDetail $item")
            }

            override fun onRetry() {
                Timber.e("retry")
                viewModel.retry()
            }
        }
        viewModel.viewState?.observe(viewLifecycleOwner, Observer {
            it?.articles?.run { newsArticleAdapter.submitList(this)  }
            it?.loadingState?.run { newsArticleAdapter.setLoading(this) }
        })
        edtSearch.addTextChangedListener(object : TextWatcher{
            override fun afterTextChanged(s: Editable?) {
                val query = s.toString()
                viewModel.search(query, viewLifecycleOwner)
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }
        })
    }

    private fun notifyNoNetworkConnection() {
        informMessage(context?.getString(R.string.message_no_network) ?: "")
    }

    private fun notifyUnknownError() {
        informMessage(context?.getString(R.string.message_unknown_error) ?: "")
    }

    private fun informMessage(message: String) {
        Toast.makeText(
            context,
            message,
            Toast.LENGTH_LONG
        ).show()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
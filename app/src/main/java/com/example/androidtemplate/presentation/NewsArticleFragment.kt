package com.example.androidtemplate.presentation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.androidtemplate.R
import com.example.androidtemplate.data.ArticleMapper
import com.example.androidtemplate.data.NewsRepository
import com.example.androidtemplate.data.SourceMapper
import com.example.androidtemplate.data.local.NewsDatabase
import com.example.androidtemplate.data.local.NewsLocalDataSource
import com.example.androidtemplate.data.remote.ApiClient
import com.example.androidtemplate.data.remote.ApiInterface
import com.example.androidtemplate.data.remote.NewsRemoteDataSource
import com.example.androidtemplate.domain.usecase.GetArticlesBySource
import com.example.androidtemplate.helper.Error
import kotlinx.android.synthetic.main.fragment_news_article_list.*

/**
 * Created by Yossi Segev on 11/11/2017.
 */
class NewsArticleFragment : Fragment() {
    private lateinit var factory: NewsArticleViewModelFactory
    private lateinit var viewModel: NewsArticleViewModel
    private lateinit var recyclerView: RecyclerView
    private lateinit var progressBar: ProgressBar
    private lateinit var newsArticleAdapter: NewsArticleAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val client = ApiClient.instance(context!!)
        val api = client.create(ApiInterface::class.java)
        val articleMapper = ArticleMapper()
        val sourceMapper = SourceMapper()
        val remoteDataSource = NewsRemoteDataSource(api, articleMapper, sourceMapper)

        val db = NewsDatabase.getInstance(Any())
        val localDataSource = NewsLocalDataSource(db, articleMapper, sourceMapper)
        val repository = NewsRepository(remoteDataSource, localDataSource)
        val getArticleBySourceUseCase = GetArticlesBySource(repository)

        factory = NewsArticleViewModelFactory(getArticleBySourceUseCase)
        viewModel = ViewModelProvider(this, factory).get(NewsArticleViewModel::class.java)

        if (savedInstanceState == null) {
            viewModel.getArticles()
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return layoutInflater.inflate(R.layout.fragment_news_article_list, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressBar = pb
        newsArticleAdapter = NewsArticleAdapter()
        recyclerView = rv
        recyclerView.layoutManager = LinearLayoutManager(context)
        recyclerView.adapter = newsArticleAdapter

        viewModel.viewState.observe(viewLifecycleOwner, Observer {
            progressBar.visibility = if (it.isLoading) View.VISIBLE else View.GONE
            newsArticleAdapter.update(it.articles?: emptyList())
            it.error?.run {
                when (this){
                    is Error.NetworkError -> {
                        notifyNoNetworkConnection()
                    }
                    else -> {
                        notifyUnknownError()
                    }
                }
            }
        })
    }

    private fun notifyNoNetworkConnection(){
        informMessage(context?.getString(R.string.message_no_network)?:"")
    }

    private fun notifyUnknownError(){
        informMessage(context?.getString(R.string.message_unknown_error)?:"")
    }

    private fun informMessage(message: String){
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
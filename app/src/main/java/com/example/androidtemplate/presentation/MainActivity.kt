package com.example.androidtemplate.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import com.example.androidtemplate.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), BottomNavigationView.OnNavigationItemSelectedListener {

    private lateinit var bottomNavigationBar: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            navigateToPage(ARTICLE_PAGE)
        }

        bottomNavigationBar = bnv
        bottomNavigationBar.setOnNavigationItemSelectedListener(this)
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        if (item.itemId == bnv.selectedItemId) {
            return false
        }

        when (item.itemId) {
            R.id.action_news -> {
                navigateToPage(ARTICLE_PAGE)
            }

            R.id.action_favourite -> {
                navigateToPage(FAVOURITE_PAGE)
            }

            R.id.action_search -> {
                navigateToPage(SEARCH_PAGE)
            }
        }
        return true
    }

    private fun navigateToPage(page: String){
        val fragment = when (page){
            ARTICLE_PAGE -> NewsArticleFragment()
            FAVOURITE_PAGE -> NewsArticleFragment()
            else -> NewsSearchArticleFragment()
        }
        supportFragmentManager.beginTransaction()
            .replace(R.id.flContainer, fragment, page)
            .commitAllowingStateLoss()
    }

    companion object{
        const val ARTICLE_PAGE = "article"
        const val FAVOURITE_PAGE = "favourite"
        const val SEARCH_PAGE = "search"
    }
}

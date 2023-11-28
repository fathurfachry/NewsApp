package com.farrell.muslimpedia

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.farrell.muslimpedia.adapter.NewsAdapter
import com.farrell.muslimpedia.data.repository.NewsRepository
import com.farrell.muslimpedia.databinding.ActivityMainBinding
import com.farrell.muslimpedia.databinding.ActivitySearchAbleBinding
import com.farrell.muslimpedia.ui.NewsViewModel
import com.farrell.muslimpedia.utils.NewsViewModelFactory
import retrofit2.http.Query

class SearchAbleActivity : AppCompatActivity() {
    lateinit var binding: ActivitySearchAbleBinding

    private val searchViewModel : NewsViewModel by viewModels{
        NewsViewModelFactory(NewsRepository())
    }

    private var querySearch: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySearchAbleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        handleIntentFromMain(intent)

        searchViewModel.searchNews.observe(this@SearchAbleActivity) { dataSearch ->
            binding.apply {
                if (dataSearch.articles.isEmpty()) {
                    tvNoNews.text = "No News"
                    tvNoNews.visibility = View.VISIBLE

                } else {
                    rvSearchResults.apply {
                        val mAdapter = NewsAdapter()
                        mAdapter.setData(dataSearch.articles)

                        adapter = mAdapter
                        layoutManager = LinearLayoutManager(this@SearchAbleActivity)
                        visibility = View.VISIBLE
                    }
                }
            }
        }

        searchViewModel.isLoading.observe(this@SearchAbleActivity){
            showLoading(it)
        }

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        binding.searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        setIntent(intent)
        handleIntentFromMain(intent)
    }

    private fun showLoading(isLoading: Boolean) {
        if (isLoading){
            binding.apply {
                binding.loadingView.root.visibility = View.VISIBLE
            }
        } else {
            binding.apply {
                binding.loadingView.root.visibility = View.GONE
            }
        }
    }

    private fun handleIntentFromMain(intent: Intent) {
        if (Intent.ACTION_SEARCH == intent.action) {
            intent.getStringExtra(SearchManager.QUERY)?.also { query ->
                querySearch = query

                binding.apply {
                    rvSearchResults.visibility = View.GONE
                    tvNoNews.visibility = View.INVISIBLE
                    searchView.setQuery("", false)
                    searchView.queryHint = query
                    searchView.clearFocus()
                }
                doMySearch(query)
            }
        }
    }

    private fun doMySearch(query: String) {
        searchViewModel.getSearchNews(query)
    }
}
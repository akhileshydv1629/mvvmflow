package com.akhilesh.newsapp.ui.search

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.akhilesh.newsapp.data.model.topheadlines.APIArticle
import com.akhilesh.newsapp.databinding.ActivitySearchBinding
import com.akhilesh.newsapp.di.component.ActivityComponent
import com.akhilesh.newsapp.ui.base.BaseActivity
import com.akhilesh.newsapp.ui.newsListScreen.NewsListAdapter
import com.akhilesh.newsapp.utils.Status
import com.akhilesh.newsapp.utils.getQueryTextChangeStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchActivity : BaseActivity<SearchViewModel, ActivitySearchBinding>() {

    @Inject
    lateinit var newsLisAdapter: NewsListAdapter

    companion object {
        fun getStartIntent(context: Context): Intent {
            return Intent(context, SearchActivity::class.java)
        }
    }


    override fun setupObserver() {
        viewModel.setUpSearchStateFlow(binding.searchView.getQueryTextChangeStateFlow())
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.data.collect {
                    when (it.status) {
                        Status.LOADING -> {
                            binding.progressBar.visibility = View.VISIBLE
                            binding.recyclerView.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.GONE
                        }
                        Status.ERROR -> {
                            binding.progressBar.visibility = View.GONE
                            binding.includeLayout.errorLayout.visibility = View.VISIBLE
                            Toast.makeText(this@SearchActivity, it.message, Toast.LENGTH_LONG)
                                .show()
                        }
                        Status.SUCCESS -> {
                            binding.includeLayout.errorLayout.visibility = View.GONE
                            binding.progressBar.visibility = View.GONE
                            it.data?.let { newsList ->
                                renderList(newsList as List<APIArticle>)
                            }
                            binding.recyclerView.visibility = View.VISIBLE
                        }
                    }
                }
            }
        }
    }


    private fun renderList(newsList: List<APIArticle>) {
        newsLisAdapter.replaceData(newsList)
        newsLisAdapter.notifyDataSetChanged()
    }

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
        val recyclerView = binding.recyclerView
        recyclerView.apply {
            layoutManager = LinearLayoutManager(this.context)
            addItemDecoration(
                DividerItemDecoration(
                    recyclerView.context,
                    (recyclerView.layoutManager as LinearLayoutManager).orientation
                )
            )
            adapter = newsLisAdapter
        }
        binding.includeLayout.tryAgainBtn.setOnClickListener {
            viewModel.fetchNewsByQueries(binding.searchView.query.toString())
        }

        newsLisAdapter.itemClickListener = { _, data ->
            val article = data as APIArticle
            val builder = CustomTabsIntent.Builder()
            val customTabsIntent = builder.build()
            customTabsIntent.launchUrl(this@SearchActivity, Uri.parse(article.url))
        }
    }

    override fun setupViewBinding(inflater: LayoutInflater): ActivitySearchBinding {
        return ActivitySearchBinding.inflate(inflater)
    }
}
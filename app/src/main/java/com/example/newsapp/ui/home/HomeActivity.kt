package com.example.newsapp.ui.home

import android.content.ContentValues.TAG
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.data.ArticleDatabase
import com.example.newsapp.data.repository.ArticleRepository
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.interfaces.DataInterface
import com.example.newsapp.model.Article
import com.example.newsapp.ui.NewsAdapter
import com.example.newsapp.ui.NewsViewModelProviderFactory
import com.example.newsapp.ui.detail.DetailActivity
import com.example.newsapp.ui.favorite.FavoriteActivity
import com.example.newsapp.utils.Resource
import com.example.newsapp.utils.alertDialog
import com.example.newsapp.utils.show

class HomeActivity : AppCompatActivity(), DataInterface {

    private lateinit var binding: ActivityHomeBinding
    private lateinit var adapter: NewsAdapter

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbarHome)
        supportActionBar?.title = ""

        adapter = NewsAdapter()
        adapter.dataInterface = this
        binding.rvHome.adapter = adapter

        val newsRepository = ArticleRepository(ArticleDatabase.getInstance(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        observeRecylerView()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_home, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> {
                startActivity(Intent(this, FavoriteActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun observeRecylerView() {
        viewModel.breakingNews.observe(this, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    showProgressBar(false)
                    showEmptyLayout(false)
                    response.data?.let { newsResponse ->
                        adapter.differ.submitList(newsResponse.articles)
                    }
                }
                is Resource.Error -> {
                    showProgressBar(false)
                    response.message?.let { message ->
                        alertDialog(this, getString(R.string.error), getString(R.string.check_your_internet))
                        Log.e(TAG, "An error occured: $message")
                    }
                    showEmptyLayout(true)
                }
                is Resource.Loading -> {
                    showProgressBar(true)
                }
            }
        })
    }

    private fun showProgressBar(flag : Boolean) {
        binding.progressbarHome.show(flag)
    }

    private fun showEmptyLayout(flag: Boolean) {
        binding.notFoundLayout.show(flag)
        binding.rvHome.show(!flag)
    }

    override fun onClicked(view: View, data: Article) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.ARTICLE, data)
        startActivity(intent)
    }

}
package com.example.newsapp.ui.favorite

import android.content.ContentValues
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.data.ArticleDatabase
import com.example.newsapp.data.repository.ArticleRepository
import com.example.newsapp.databinding.ActivityFavoriteBinding
import com.example.newsapp.interfaces.DataInterface
import com.example.newsapp.model.Article
import com.example.newsapp.ui.NewsAdapter
import com.example.newsapp.ui.NewsViewModelProviderFactory
import com.example.newsapp.ui.detail.DetailActivity
import com.example.newsapp.ui.home.NewsViewModel
import com.example.newsapp.utils.Resource
import com.example.newsapp.utils.alertDialog
import com.example.newsapp.utils.show

class FavoriteActivity : AppCompatActivity(), DataInterface {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var adapter: NewsAdapter
    private lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = NewsAdapter()
        adapter.dataInterface = this
        binding.rvFavorite.adapter = adapter

        val newsRepository = ArticleRepository(ArticleDatabase.getInstance(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        observeRecylerView()

        binding.ivActionBack.setOnClickListener{
            onBackPressed()
        }
    }

    private fun observeRecylerView() {
        viewModel.getSavedNews().observe(this, Observer { articles ->
            if (articles != null)  {
                showEmptyLayout(false)
                adapter.differ.submitList(articles)
            }
            else showEmptyLayout(true)
        })
    }

    private fun showEmptyLayout(flag: Boolean) {
        binding.notFoundLayout.show(flag)
        binding.rvFavorite.show(!flag)
    }

    override fun onClicked(view: View, data: Article) {
        val intent = Intent(this, DetailActivity::class.java)
        intent.putExtra(DetailActivity.ARTICLE, data)
        startActivity(intent)
    }
}
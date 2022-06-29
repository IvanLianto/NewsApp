package com.example.newsapp.ui.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.newsapp.R
import com.example.newsapp.data.ArticleDatabase
import com.example.newsapp.data.repository.ArticleRepository
import com.example.newsapp.databinding.ActivityDetailBinding
import com.example.newsapp.databinding.ActivityHomeBinding
import com.example.newsapp.model.Article
import com.example.newsapp.ui.NewsViewModelProviderFactory
import com.example.newsapp.ui.home.HomeActivity
import com.example.newsapp.ui.home.NewsViewModel
import com.example.newsapp.utils.favoriteDialog
import com.example.newsapp.utils.unFavoriteDialog
import com.google.android.material.snackbar.Snackbar

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding
    lateinit var viewModel: NewsViewModel

    private var isFavorite: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val article = intent.getParcelableExtra<Article>(ARTICLE) as Article

        val newsRepository = ArticleRepository(ArticleDatabase.getInstance(this))
        val viewModelProviderFactory = NewsViewModelProviderFactory(newsRepository)
        viewModel = ViewModelProvider(this, viewModelProviderFactory).get(NewsViewModel::class.java)

        checkIsFavorite(article)

        binding.webView.apply {
            webViewClient = WebViewClient()
            loadUrl(article.url)
        }

        binding.fab.setOnClickListener {
            setFavorite(article)
        }

    }

    private fun checkIsFavorite(article: Article){
        viewModel.checkIsFavorite(article.title).observe(this) { result ->
            isFavorite = result != null
            setIconFavorite(isFavorite)
        }
    }

    private fun setFavorite(article: Article) {
        if (!isFavorite) {
            favoriteDialog(
                this,
                getString(R.string.favorite),
                getString(R.string.message_favorite),
                article,
                viewModel
            )
        } else {
            unFavoriteDialog(
                this,
                getString(R.string.unfavorite),
                getString(R.string.message_unfavorite),
                article,
                viewModel
            )
        }
        checkIsFavorite(article)
        setIconFavorite(isFavorite)
    }

    private fun setIconFavorite(flag: Boolean) {
        if (flag) {
            binding.fab.setImageResource(R.drawable.ic_baseline_favorite_24)
        } else {
            binding.fab.setImageResource(R.drawable.ic_baseline_favorite_border_24)
        }
    }

    companion object {
        const val ARTICLE = "article"
    }
}
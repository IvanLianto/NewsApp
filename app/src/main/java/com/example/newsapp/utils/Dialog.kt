package com.example.newsapp.utils

import android.app.AlertDialog
import android.content.Context
import com.example.newsapp.R
import com.example.newsapp.model.Article
import com.example.newsapp.ui.home.NewsViewModel
import kotlin.system.exitProcess

fun alertDialog(context: Context, title: String, message: String) {
    val builder = AlertDialog.Builder(context)
    with(builder)
    {
        setTitle(title)
        setMessage(message)
        setPositiveButton(
            context.getString(R.string.ok)
        ) { dialog, _ ->
            dialog.dismiss()
            exitProcess(0)
        }
        setCancelable(false)
        show()
    }
}

fun favoriteDialog(
    context: Context,
    title: String,
    message: String,
    data: Article,
    viewModel: NewsViewModel
) {
    val builder = AlertDialog.Builder(context)
    builder.apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(
            context.getString(R.string.ok)
        ) { dialog, _ ->
            viewModel.saveArticle(data)
            dialog.dismiss()
            confirmDialog(
                context,
                context.resources.getString(R.string.success),
                context.resources.getString(R.string.success_favorite)
            )
        }
        setNegativeButton(
            context.resources.getString(R.string.no)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        setCancelable(false)
        show()
    }
}

fun unFavoriteDialog(
    context: Context,
    title: String,
    message: String,
    data: Article,
    viewModel: NewsViewModel
) {
    val builder = AlertDialog.Builder(context)
    builder.apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(
            context.getString(R.string.ok)
        ) { dialog, _ ->
            viewModel.deleteArticle(data)
            dialog.dismiss()
            confirmDialog(
                context,
                context.resources.getString(R.string.success),
                context.resources.getString(R.string.success_unfavorite)
            )
        }
        setNegativeButton(
            context.resources.getString(R.string.no)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        setCancelable(false)
        show()
    }
}

fun confirmDialog(context: Context, title: String, message: String) {
    val builder = AlertDialog.Builder(context)
    builder.apply {
        setTitle(title)
        setMessage(message)
        setPositiveButton(
            context.getString(R.string.ok)
        ) { dialog, _ ->
            dialog.dismiss()
        }
        setCancelable(false)
        show()
    }
}
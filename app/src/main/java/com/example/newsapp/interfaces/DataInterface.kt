package com.example.newsapp.interfaces

import android.view.View
import com.example.newsapp.model.Article

interface DataInterface {
    fun onClicked(view: View, data: Article)
}
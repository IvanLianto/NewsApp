package com.example.newsapp.network

import com.example.newsapp.model.Article
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ApiResponse(
    @Expose
    @SerializedName("articles") val articles: List<Article>,
    @Expose
    @SerializedName("status") val status: String,
    @Expose
    @SerializedName("totalResults")  val totalResults: Int
)
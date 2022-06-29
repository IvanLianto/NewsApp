package com.example.newsapp.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Entity(tableName = "article")
@Parcelize
data class Article(
    @PrimaryKey(autoGenerate = true)
    @Expose
    @ColumnInfo(name = "id")
    var id: Int? = null,
    @Expose
    @ColumnInfo(name = "author")
    @field:SerializedName("author")
    val author: String,
    @Expose
    @ColumnInfo(name = "title")
    @field:SerializedName("title")
    val title: String,
    @Expose
    @ColumnInfo(name = "url")
    @field:SerializedName("url")
    val url: String,
    @Expose
    @ColumnInfo(name = "urlToImage")
    @field:SerializedName("urlToImage")
    val urlToImage: String
) : Parcelable

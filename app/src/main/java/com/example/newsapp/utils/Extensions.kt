package com.example.newsapp.utils

import android.view.View

fun View.show(flag : Boolean) {
    visibility = if (flag) View.VISIBLE else View.GONE
}
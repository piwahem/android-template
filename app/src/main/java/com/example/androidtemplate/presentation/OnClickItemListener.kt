package com.example.androidtemplate.presentation

interface OnClickItemListener<T> {
    fun onClickItem(item: T)
    fun onRetry()
}
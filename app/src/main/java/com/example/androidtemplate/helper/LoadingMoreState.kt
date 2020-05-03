package com.example.androidtemplate.helper

sealed class LoadingMoreState {
    class Loading(): LoadingMoreState()
    class Error(): LoadingMoreState()
    class Done(): LoadingMoreState()
}
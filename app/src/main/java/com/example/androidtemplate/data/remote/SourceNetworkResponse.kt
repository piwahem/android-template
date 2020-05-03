package com.example.androidtemplate.data.remote

data class SourceNetworkResponse(
    val sources: List<Source>,
    val status: String
){
    data class Source(
        val category: String,
        val country: String,
        val description: String,
        val id: String,
        val language: String,
        val name: String,
        val url: String
    )
}
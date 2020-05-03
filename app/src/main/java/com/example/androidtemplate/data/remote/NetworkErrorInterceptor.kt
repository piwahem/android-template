package com.example.androidtemplate.data.remote

import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import java.io.IOException

class NetworkErrorInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        var newRequest = chain.request().newBuilder().build()
        return onOnIntercept(
            chain,
            newRequest
        )
    }

    @Throws(IOException::class)
    private fun onOnIntercept(chain: Interceptor.Chain, request: Request): Response {
        return try {
            chain.proceed(request)
        } catch (e: IOException) {
            val response = chain.proceed(chain.request())
            response.newBuilder().body(
                ResponseBody.create(
                    response.body!!.contentType()
                    , "ERROR"
                )
            ).build()
        }
    }
}
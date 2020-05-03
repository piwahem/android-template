package com.example.androidtemplate.data.remote

import android.content.Context
import android.os.Build
import com.example.androidtemplate.helper.TLSSocketFactory
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    private var retrofit: Retrofit? = null

    companion object {
        // TODO refactor this whole class later, bad practice
        var context: Context? = null
        fun instance(context: Context): Retrofit {
            this.context = context
            return ApiClient().getClient(context)
        }
    }

    /**
     * Set base url API
     */
    private fun baseUrl(): String {
        return "https://newsapi.org/"
    }

    /**
     * Call service with retrofit
     */
    private fun getClient(context: Context): Retrofit {
        val okhttpBuilder = OkHttpClient.Builder()
        okhttpBuilder.connectTimeout(10, TimeUnit.SECONDS)
        okhttpBuilder.writeTimeout(10, TimeUnit.SECONDS)
        okhttpBuilder.readTimeout(10, TimeUnit.SECONDS)

        okhttpBuilder.addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
        okhttpBuilder.addInterceptor(APIKeyInterceptor())
        okhttpBuilder.addInterceptor(NetworkErrorInterceptor())
        okhttpBuilder.apply {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                val tlsSocketFactory = TLSSocketFactory()
                sslSocketFactory(tlsSocketFactory, tlsSocketFactory.trustManager)
            }
        }

        val gson = GsonBuilder()
            .setLenient()
            .create()
        retrofit = Retrofit.Builder()
            .baseUrl(baseUrl())
            .client(okhttpBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
//            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
        return retrofit!!
    }
}
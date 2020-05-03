package com.example.androidtemplate.extension

import com.example.androidtemplate.helper.Error
import retrofit2.Response
import com.example.androidtemplate.helper.Result
import kotlinx.coroutines.CoroutineScope
import java.io.IOException

inline fun <T> CoroutineScope.executeRequest(request: () -> Response<T>): Result<T, Error> {
    return try {
        val response = request.invoke()
        if (response.isSuccessful) {
            response.body()?.let {
                Result.Success(it)
            } ?: run {
                Result.Failure(Error.EmptyBodyError())
            }
        } else {
            Result.Failure(Error.RandomError(Throwable(response.errorBody()?.string())))
        }
    } catch (e: Exception) {
        e.printStackTrace()
        when (e) {
            is IOException -> {
                Result.Failure(Error.NetworkError())
            }
            else -> {
                Result.Failure(Error.RandomError(e.cause))
            }
        }
    }
}

package com.example.androidtemplate.domain.usecase

import com.example.androidtemplate.helper.Result
import com.example.androidtemplate.helper.Error

interface IBaseUseCase<T> {
    suspend fun create(data: Map<String, Any>? = null):  Result<T, Error>
}